package com.vote.dao.mapper;

import com.vote.dao.util.MyMapper;
import com.vote.model.VoteRecord;
import com.vote.vo.resp.system.CandidateListResp;
import com.vote.vo.resp.system.RecordListResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoteRecordMapper extends MyMapper<VoteRecord> {

    /**
     * 根据选举id和候选人id查询票数
     * @param candidateId 候选人id
     * @return
     */
    Integer findCountByCandidateId(@Param("candidateId") Long candidateId);

    /**
     * 查询投给候选人的用户列表
     * @param candidateId 候选人id
     * @return
     */
    List<RecordListResp> findRecordListByCandidateId(@Param("candidateId")Long candidateId);

    /**
     * 根据选举id和用户id查询用户投票
     * @param voteId 选举id
     * @param userId 用户id
     * @return
     */
    VoteRecord findRecordByUserId(@Param("voteId")Long voteId, @Param("userId")Long userId);
}