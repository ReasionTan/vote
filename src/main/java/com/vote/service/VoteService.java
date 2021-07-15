package com.vote.service;

import com.vote.basic.response.ResponseWrap;
import com.vote.vo.req.app.VoteReq;
import com.vote.vo.resp.system.CandidateListResp;

import java.util.List;

public interface VoteService {

    /**
     * 用户登记
     * @param idCard 身份证
     * @param email  邮箱
     * @return
     */
    ResponseWrap<Long> register(String idCard, String email);

    /**
     * 用户投票
     * @param voteReq
     * @return
     */
    ResponseWrap<List<CandidateListResp>> vote(VoteReq voteReq);
}
