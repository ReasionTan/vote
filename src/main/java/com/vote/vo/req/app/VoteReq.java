package com.vote.vo.req.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("用户投票请求VO")
public class VoteReq {

    @NotNull(message = "请输入选举id")
    @ApiModelProperty(value = "选举id", required = true)
    private Long voteId;

    @NotNull(message = "请输入候选人id")
    @ApiModelProperty(value = "候选人id", required = true)
    private Long candidateId;

    @NotNull(message = "请输入用户id")
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VoteReq{" +
                "voteId=" + voteId +
                ", candidateId=" + candidateId +
                ", userId=" + userId +
                '}';
    }
}
