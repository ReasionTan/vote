package com.vote.dao.mapper;

import com.vote.dao.util.MyMapper;
import com.vote.model.VoteUser;
import org.apache.ibatis.annotations.Param;

public interface VoteUserMapper extends MyMapper<VoteUser> {

    /**
     * 根据idCard查询用户登记信息
     * @param idCard 身份证
     * @return
     */
    VoteUser getVoteUserByIdCard(@Param("idCard") String idCard);
}