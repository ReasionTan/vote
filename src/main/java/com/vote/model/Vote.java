package com.vote.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "vote")
public class Vote implements Serializable {
    /**
     * 选举主键
     */
    @Id
    private Long id;

    /**
     * 选举名字
     */
    private String name;

    /**
     * 状态：0：未开始，1：已开始，2：已结束
     */
    private Integer status;

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
     * 获取选举主键
     *
     * @return id - 选举主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置选举主键
     *
     * @param id 选举主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取选举名字
     *
     * @return name - 选举名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置选举名字
     *
     * @param name 选举名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取状态：0：未开始，1：已开始，2：已结束
     *
     * @return status - 状态：0：未开始，1：已开始，2：已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：0：未开始，1：已开始，2：已结束
     *
     * @param status 状态：0：未开始，1：已开始，2：已结束
     */
    public void setStatus(Integer status) {
        this.status = status;
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
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
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
        Vote other = (Vote) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}