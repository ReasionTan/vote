package com.vote.vo.resp.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询候选人列表及其票数返回VO")
public class CandidateListResp {

    @ApiModelProperty(value = "候选人id")
    private Long id;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "票数")
    private Integer poll;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPoll() {
        return poll;
    }

    public void setPoll(Integer poll) {
        this.poll = poll;
    }

    @Override
    public String toString() {
        return "CandidateListResp{" +
                "id=" + id +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                ", poll=" + poll +
                '}';
    }
}
