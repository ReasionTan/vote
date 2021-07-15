package com.vote;

import com.vote.basic.response.ResponseWrap;
import com.vote.service.VoteAdminService;
import com.vote.vo.req.system.RecordListReq;
import com.vote.vo.req.system.VoteCandidateReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VoteAdminServiceTest extends VoteApplicationTest {

    @Autowired
    private VoteAdminService voteAdminService;

    @Test
    public void addVote() {
        ResponseWrap responseWrap = voteAdminService.addVote("2020年度优秀议员选举");
        System.out.println(responseWrap.toString());
    }

    @Test
    public void updateVoteStatus() {
        ResponseWrap responseWrap = voteAdminService.updateVoteStatus(5525435452507168L, 2);
        System.out.println(responseWrap.toString());
    }

    @Test
    public void addCandidate() {
        VoteCandidateReq voteCandidateReq = new VoteCandidateReq();
        voteCandidateReq.setIdCard("A132856(7)");
        voteCandidateReq.setName("付款咯");
        voteCandidateReq.setVoteId(5525435452507168L);
        ResponseWrap responseWrap = voteAdminService.addCandidate(voteCandidateReq);
        System.out.println(responseWrap.toString());
    }

    @Test
    public void getRecordList() {
        RecordListReq recordListReq = new RecordListReq();
        recordListReq.setCandidateId(5526729673262112L);
        ResponseWrap responseWrap = voteAdminService.getRecordList(recordListReq);
        System.out.println(responseWrap.toString());
    }

    @Test
    public void getVoteResult() {
        ResponseWrap responseWrap = voteAdminService.getVoteResult(5525435452507168L);
        System.out.println(responseWrap.toString());
    }
}
