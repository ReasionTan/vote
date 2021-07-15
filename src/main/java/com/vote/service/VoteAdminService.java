package com.vote.service;

import com.vote.basic.response.PageResponse;
import com.vote.basic.response.ResponseWrap;
import com.vote.vo.req.system.RecordListReq;
import com.vote.vo.req.system.VoteCandidateReq;
import com.vote.vo.resp.system.CandidateListResp;
import com.vote.vo.resp.system.RecordListResp;

import java.util.List;

public interface VoteAdminService {

    /**
     * 添加选举
     * @param name 选举名称
     * @return
     */
    ResponseWrap addVote(String name);

    /**
     * 更新选举状态
     * @param voteId 选举id
     * @param status 选举状态：1：开始，2：结束
     * @return
     */
    ResponseWrap updateVoteStatus(Long voteId, Integer status);

    /**
     * 添加选举候选人
     * @param voteCandidateReq
     * @return
     */
    ResponseWrap addCandidate(VoteCandidateReq voteCandidateReq);

    /**
     * 查询投给候选人的用户列表
     * @param recordListReq
     * @return
     */
    ResponseWrap<PageResponse<RecordListResp>> getRecordList(RecordListReq recordListReq);


    /**
     * 查询选举最终结果
     * @param voteId 选举id
     * @return
     */
    ResponseWrap<List<CandidateListResp>> getVoteResult(Long voteId);
}
