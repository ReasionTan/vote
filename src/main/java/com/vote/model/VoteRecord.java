package com.vote.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "vote_record")
public class VoteRecord implements Serializable {
    /**
     * 选举记录id
     */
    @Id
    private Long id;

    /**
     * 选举id
     */
    @Column(name = "vote_id")
    private Long voteId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 候选人id
     */
    @Column(name = "candidate_id")
    private Long candidateId;

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
     * 获取选举记录id
     *
     * @return id - 选举记录id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置选举记录id
     *
     * @param id 选举记录id
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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取候选人id
     *
     * @return candidate_id - 候选人id
     */
    public Long getCandidateId() {
        return candidateId;
    }

    /**
     * 设置候选人id
     *
     * @param candidateId 候选人id
     */
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
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
        sb.append(", userId=").append(userId);
        sb.append(", candidateId=").append(candidateId);
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
        VoteRecord other = (VoteRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVoteId() == null ? other.getVoteId() == null : this.getVoteId().equals(other.getVoteId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCandidateId() == null ? other.getCandidateId() == null : this.getCandidateId().equals(other.getCandidateId()))
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
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCandidateId() == null) ? 0 : getCandidateId().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}