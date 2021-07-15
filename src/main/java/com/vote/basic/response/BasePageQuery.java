package com.vote.basic.response;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BasePageQuery implements Serializable {

    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer DEFAULT_FIRST_PAGE = 1;

    @ApiModelProperty("每页显示的大小")
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    @ApiModelProperty("当前页数")
    private Integer currentPage = DEFAULT_FIRST_PAGE;

    @ApiModelProperty("排序,desc或asc")
    private String order;

    @ApiModelProperty("需要排序的字段")
    private String sort;

    public BasePageQuery() {
    }

    public BasePageQuery(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public Integer getCurrentPage() {
        if (this.currentPage == null || this.currentPage <= 0) {
            return DEFAULT_FIRST_PAGE;
        }

        return this.currentPage;
    }

    public void setCurrentPage(Integer cPage) {
        if (cPage == null || cPage <= 0) {
            this.currentPage = DEFAULT_FIRST_PAGE;
        } else {
            this.currentPage = cPage;
        }
    }

    public Integer getPageSize() {
        if (this.pageSize == null || this.pageSize <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        return this.pageSize;
    }

    public boolean hasSetPageSize() {
        return this.pageSize != null;
    }

    public void setPageSize(Integer pSize) {
        if (pSize == null || pSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        this.pageSize = pSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "BasePageQuery{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", order='" + order + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}