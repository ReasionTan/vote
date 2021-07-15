package com.vote.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vote.basic.constant.RedisConstant;
import com.vote.basic.response.PageResponse;
import com.vote.basic.response.ResponseCode;
import com.vote.basic.response.ResponseWrap;
import com.vote.dao.mapper.VoteCandidateMapper;
import com.vote.dao.mapper.VoteMapper;
import com.vote.dao.mapper.VoteRecordMapper;
import com.vote.model.Vote;
import com.vote.model.VoteCandidate;
import com.vote.service.VoteAdminService;
import com.vote.utils.IdWorker;
import com.vote.vo.req.system.RecordListReq;
import com.vote.vo.req.system.VoteCandidateReq;
import com.vote.vo.resp.system.CandidateListResp;
import com.vote.vo.resp.system.RecordListResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VoteAdminServiceImpl implements VoteAdminService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private VoteCandidateMapper voteCandidateMapper;

    @Autowired
    private VoteRecordMapper voteRecordMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseWrap addVote(String name) {
        //查询是否已存在而且在进行中的一样名称的选举
        Vote vote = voteMapper.findVoteByName(name);
        if (null != vote){
            return ResponseWrap.failure(ResponseCode.CODE_1007);
        }
        //添加选举
        Vote newVote = new Vote();
        newVote.setId(idWorker.nextId());
        newVote.setName(name);
        newVote.setStatus(0);
        newVote.setIsDel(0);
        newVote.setCreateTime(new Date());
        newVote.setUpdateTime(new Date());
        voteMapper.insert(newVote);
        return ResponseWrap.success();
    }

    @Override
    public ResponseWrap updateVoteStatus(Long voteId, Integer status) {
        //查询选举信息
        Vote vote = voteMapper.selectByPrimaryKey(voteId);
        if (null == vote){
            return ResponseWrap.failure(ResponseCode.CODE_1008);
        }
        //判断状态是否是所需更新状态
        if (vote.getStatus() == status){
            return ResponseWrap.failure(ResponseCode.CODE_1009);
        }
        //如果选举要开始，判断是否已结束和候选人数量是否大于2
        if (status == 1){
            if (vote.getStatus() == 2){
                return ResponseWrap.failure(ResponseCode.CODE_1011);
            }
            //查询候选人数量
            Integer count = voteCandidateMapper.findCandidateCountByVoteId(voteId);
            if (count < 2){
                return ResponseWrap.failure(ResponseCode.CODE_1012);
            }
        }
        //查询候选人列表
        List<VoteCandidate> candidates = voteCandidateMapper.findCandidatesByVoteId(voteId);
        //选举开始初始化投票数量
        if (status == 1){
            candidates.forEach( x -> redisTemplate.opsForValue().setIfAbsent(RedisConstant.VOTE_CANDIDATE_COUNT + x.getId(), 0));
            //将选举人缓存到redis
            redisTemplate.opsForValue().set(RedisConstant.VOTE_ID + voteId, JSON.toJSONString(candidates));
        }
        //选举结束就删除redis候选人票数
        if (status == 2){
            candidates.forEach( x -> redisTemplate.delete(RedisConstant.VOTE_CANDIDATE_COUNT + x.getId()));
            redisTemplate.delete(RedisConstant.VOTE_ID + voteId);
        }
        //更新状态
        voteMapper.updateVoteStatusById(voteId, status);
        return ResponseWrap.success();
    }

    @Override
    public ResponseWrap addCandidate(VoteCandidateReq voteCandidateReq) {
        //查询选举信息
        Vote vote = voteMapper.selectByPrimaryKey(voteCandidateReq.getVoteId());
        if (null == vote){
            return ResponseWrap.failure(ResponseCode.CODE_1008);
        }
        //判断是否已结束
        if (vote.getStatus() == 2){
            return ResponseWrap.failure(ResponseCode.CODE_1011);
        }
        //判断当前选举候选人是否已存在
        VoteCandidate voteCandidate = voteCandidateMapper.findVoteCandidateByIdCard(voteCandidateReq.getVoteId(), voteCandidateReq.getIdCard());
        if (null != voteCandidate){
            return ResponseWrap.failure(ResponseCode.CODE_1010);
        }
        //添加候选人信息
        VoteCandidate newCandidate = new VoteCandidate();
        Long id = idWorker.nextId();
        newCandidate.setId(id);
        BeanUtils.copyProperties(voteCandidateReq, newCandidate);
        newCandidate.setPoll(0);
        newCandidate.setIsDel(0);
        newCandidate.setCreateTime(new Date());
        newCandidate.setUpdateTime(new Date());
        voteCandidateMapper.insert(newCandidate);
        return ResponseWrap.success();
    }

    @Override
    public ResponseWrap<PageResponse<RecordListResp>> getRecordList(RecordListReq recordListReq) {
        PageHelper.startPage(recordListReq.getCurrentPage(), recordListReq.getPageSize());
        //查询列表
        List<RecordListResp> recordList = voteRecordMapper.findRecordListByCandidateId(recordListReq.getCandidateId());
        return ResponseWrap.successPage(PageInfo.of(recordList));
    }

    @Override
    public ResponseWrap<List<CandidateListResp>> getVoteResult(Long voteId) {
        //查询选举信息
        Vote vote = voteMapper.selectByPrimaryKey(voteId);
        if (null == vote){
            return ResponseWrap.failure(ResponseCode.CODE_1008);
        }
        //查询列表
        List<CandidateListResp> result = voteCandidateMapper.findCandidateListByVoteId(voteId);
        return ResponseWrap.success(result);
    }
}
