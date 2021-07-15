package com.vote.vo.req.system;

import com.vote.basic.response.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("查询投给候选人的用户列表请求VO")
public class RecordListReq extends BasePageQuery {

    @NotNull(message = "请输入候选人id")
    @ApiModelProperty(value = "候选人id", required = true)
    private Long candidateId;

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "RecordListReq{" +
                "candidateId=" + candidateId +
                '}';
    }
}
