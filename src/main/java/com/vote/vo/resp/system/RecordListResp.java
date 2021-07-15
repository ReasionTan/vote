package com.vote.vo.resp.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询投给候选人的用户列表返回VO")
public class RecordListResp {

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RecordListResp{" +
                "idCard='" + idCard + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
