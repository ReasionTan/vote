package com.vote.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "vote_candidate")
public class VoteCandidate implements Serializable {
    /**
     * 选举候选人id
     */
    @Id
    private Long id;

    /**
     * 选举id
     */
    @Column(name = "vote_id")
    private Long voteId;

    /**
     * 身份证
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 名字
     */
    private String name;

    /**
     * 票数
     */
    private Integer poll;

    /**
     * 是否删除：0：否，1：是
     */
    @Column(name = "is_del")
    private Integer isDel;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取选举候选人id
     *
     * @return id - 选举候选人id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置选举候选人id
     *
     * @param id 选举候选人id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取选举id
     *
     * @return vote_id - 选举id
     */
    public Long getVoteId() {
        return voteId;
    }

    /**
     * 设置选举id
     *
     * @param voteId 选举id
     */
    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    /**
     * 获取身份证
     *
     * @return id_card - 身份证
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证
     *
     * @param idCard 身份证
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取名字
     *
     * @return name - 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名字
     *
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取票数
     *
     * @return poll - 票数
     */
    public Integer getPoll() {
        return poll;
    }

    /**
     * 设置票数
     *
     * @param poll 票数
     */
    public void setPoll(Integer poll) {
        this.poll = poll;
    }

    /**
     * 获取是否删除：0：否，1：是
     *
     * @return is_del - 是否删除：0：否，1：是
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除：0：否，1：是
     *
     * @param isDel 是否删除：0：否，1：是
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", voteId=").append(voteId);
        sb.append(", idCard=").append(idCard);
        sb.append(", name=").append(name);
        sb.append(", poll=").append(poll);
        sb.append(", isDel=").append(isDel);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        VoteCandidate other = (VoteCandidate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVoteId() == null ? other.getVoteId() == null : this.getVoteId().equals(other.getVoteId()))
            && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPoll() == null ? other.getPoll() == null : this.getPoll().equals(other.getPoll()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVoteId() == null) ? 0 : getVoteId().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPoll() == null) ? 0 : getPoll().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}