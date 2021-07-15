package com.vote.vo.req.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel("添加选举候选人请求VO")
public class VoteCandidateReq {

    @NotNull(message = "请输入选举id")
    @ApiModelProperty(value = "选举id", required = true)
    private Long voteId;

    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{6}\\(?[0-9]\\)?$", message = "请输入正确的身份证号码")
    @ApiModelProperty(value = "候选人身份证", required = true)
    private String idCard;

    @NotBlank(message = "请输入候选人名字")
    @ApiModelProperty(value = "候选人名字", required = true)
    private String name;

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VoteCandidateReq{" +
                "voteId=" + voteId +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
