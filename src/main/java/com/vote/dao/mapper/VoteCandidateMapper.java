package com.vote.dao.mapper;

import com.vote.dao.util.MyMapper;
import com.vote.model.VoteCandidate;
import com.vote.vo.resp.system.CandidateListResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoteCandidateMapper extends MyMapper<VoteCandidate> {
    /**
     * 根据选举id和候选人身份证查询候选人信息
     * @param voteId 选举id
     * @param idCard 候选人身份证
     * @return
     */
    VoteCandidate findVoteCandidateByIdCard(@Param("voteId") Long voteId, @Param("idCard") String idCard);

    /**
     * 根据选举id查询候选人数量
     * @param voteId 选举id
     * @return
     */
    Integer findCandidateCountByVoteId(@Param("voteId") Long voteId);

    /**
     * 根据选举id查询候选人列表
     * @param voteId 选举id
     * @return
     */
    List<VoteCandidate> findCandidatesByVoteId(@Param("voteId") Long voteId);

    /**
     * 根据选举id查询候选人列表
     * @param voteId 选举id
     * @return
     */
    List<CandidateListResp> findCandidateListByVoteId(@Param("voteId") Long voteId);

    /**
     * 更新票数
     * @param candidateId 候选人id
     * @return
     */
    Integer updatePollById(@Param("candidateId") Long candidateId);

}