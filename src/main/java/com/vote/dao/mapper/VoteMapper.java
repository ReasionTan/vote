package com.vote.dao.mapper;

import com.vote.dao.util.MyMapper;
import com.vote.model.Vote;
import org.apache.ibatis.annotations.Param;

public interface VoteMapper extends MyMapper<Vote> {

    /**
     * 根据名称查询选举
     * @param name 选举名称
     * @return
     */
    Vote findVoteByName(@Param("name") String name);

    /**
     * 根据id更新选举状态
     * @param voteId 选举id
     * @param status 选举状态：1：开始，2：结束
     * @return
     */
    int updateVoteStatusById(@Param("voteId") Long voteId, @Param("status") Integer status);
}