package com.vote.service.impl;

import com.alibaba.fastjson.JSON;
import com.vote.basic.constant.RedisConstant;
import com.vote.basic.response.ResponseCode;
import com.vote.basic.response.ResponseWrap;
import com.vote.dao.mapper.VoteCandidateMapper;
import com.vote.dao.mapper.VoteMapper;
import com.vote.dao.mapper.VoteRecordMapper;
import com.vote.dao.mapper.VoteUserMapper;
import com.vote.model.Vote;
import com.vote.model.VoteCandidate;
import com.vote.model.VoteRecord;
import com.vote.model.VoteUser;
import com.vote.service.VoteService;
import com.vote.utils.IdWorker;
import com.vote.vo.req.app.VoteReq;
import com.vote.vo.resp.system.CandidateListResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteUserMapper voteUserMapper;

    @Autowired
    private VoteRecordMapper voteRecordMapper;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private VoteCandidateMapper voteCandidateMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseWrap<Long> register(String idCard, String email) {
        //查询idCard，判断是否已注册
        VoteUser voteUser = voteUserMapper.getVoteUserByIdCard(idCard);
        if (null != voteUser){
            return ResponseWrap.failure(ResponseCode.CODE_1015);
        }
        VoteUser newUser = new VoteUser();
        //插入数据库
        newUser.setId(idWorker.nextId());
        newUser.setIdCard(idCard);
        newUser.setEmail(email);
        newUser.setIsDel(0);
        newUser.setCreateTime(new Date());
        newUser.setUpdateTime(new Date());
        voteUserMapper.insert(newUser);
        return ResponseWrap.success(newUser.getId());
    }

    @Override
    @Transactional
    public ResponseWrap<List<CandidateListResp>> vote(VoteReq voteReq) {
        //查询用户是否存在
        VoteUser voteUser = voteUserMapper.selectByPrimaryKey(voteReq.getUserId());
        if (null == voteUser){
            return ResponseWrap.failure(ResponseCode.CODE_1016);
        }
        //查询选举信息
        Vote vote = voteMapper.selectByPrimaryKey(voteReq.getVoteId());
        if (null == vote){
            return ResponseWrap.failure(ResponseCode.CODE_1008);
        }
        //判断是否已结束
        if (vote.getStatus() == 2){
            return ResponseWrap.failure(ResponseCode.CODE_1011);
        }
        //判断候选人是否存在
        VoteCandidate voteCandidate = voteCandidateMapper.selectByPrimaryKey(voteReq.getCandidateId());
        if (null == voteCandidate){
            return ResponseWrap.failure(ResponseCode.CODE_1013);
        }
        //查询是否已投票
        VoteRecord voteRecord = voteRecordMapper.findRecordByUserId(voteReq.getVoteId(), voteReq.getUserId());
        if (null != voteRecord){
            return ResponseWrap.failure(ResponseCode.CODE_1017);
        }
        VoteRecord record = new VoteRecord();
        record.setId(idWorker.nextId());
        record.setVoteId(voteReq.getVoteId());
        record.setUserId(voteReq.getUserId());
        record.setCandidateId(voteReq.getCandidateId());
        record.setIsDel(0);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        voteRecordMapper.insert(record);
        //修改票数
        voteCandidateMapper.updatePollById(voteReq.getCandidateId());
        //redis票数+1
        redisTemplate.opsForValue().increment(RedisConstant.VOTE_CANDIDATE_COUNT + voteReq.getCandidateId(), 1);
        //redis获取候选人列表
        String json = (String) redisTemplate.opsForValue().get(RedisConstant.VOTE_ID + voteReq.getVoteId());
        List<VoteCandidate> candidates = JSON.parseArray(json, VoteCandidate.class);
        List<CandidateListResp> candidateList = new ArrayList<>();
        for (VoteCandidate candidate : candidates){
            CandidateListResp candidateListResp = new CandidateListResp();
            BeanUtils.copyProperties(candidate, candidateListResp);
            candidateListResp.setPoll((int) redisTemplate.opsForValue().get(RedisConstant.VOTE_CANDIDATE_COUNT + candidate.getId()));
            candidateList.add(candidateListResp);
        }
        return ResponseWrap.success(candidateList);
    }
}
