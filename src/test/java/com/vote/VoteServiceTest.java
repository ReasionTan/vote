package com.vote;

import com.vote.basic.response.ResponseWrap;
import com.vote.service.VoteService;
import com.vote.vo.req.app.VoteReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VoteServiceTest extends VoteApplicationTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void register() {
        ResponseWrap responseWrap = voteService.register("A123456(4)", "123@qw");
        System.out.println(responseWrap.toString());
    }

    @Test
    public void vote() {
        VoteReq voteReq = new VoteReq();
        voteReq.setCandidateId(5526729673262112L);
        voteReq.setUserId(5526874725925920L);
        voteReq.setVoteId(5525435452507168L);
        ResponseWrap responseWrap = voteService.vote(voteReq);
        System.out.println(responseWrap.toString());
    }
}
