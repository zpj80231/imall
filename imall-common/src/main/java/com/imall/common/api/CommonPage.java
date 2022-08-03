package com.imall.common.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * @author zhangpengjun
 * @version 1.0
 * @className CommonPage
 * @description MyBatis Plus分页数据封装
 * @date 2020/9/30
 */
@Data
public class CommonPage<T> {
    /** 当前页 */
    private Long pageNum;
    /** 每页记录数 */
    private Long pageSize;
    /** 总页数 */
    private Long totalPage;
    /** 总数 */
    private Long total;
    /** 当前页的数据 */
    private List<T> list;

    public static <T> CommonPage<T> restPage(IPage<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(pageResult.getCurrent());
        result.setPageSize(pageResult.getSize());
        result.setTotal(pageResult.getTotal());
        result.setTotalPage(pageResult.getTotal()/pageResult.getSize()+1);
        result.setList(pageResult.getRecords());
        return result;
    }

    public static <T> CommonPage<T> restPage(IPage<?> pageResult, List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(pageResult.getCurrent());
        result.setPageSize(pageResult.getSize());
        result.setTotal(pageResult.getTotal());
        result.setTotalPage(pageResult.getTotal()/pageResult.getSize()+1);
        result.setList(list);
        return result;
    }

    public static <T> Page<T> getPage(Integer currPage, Integer pageSize) {
        currPage = currPage == null || currPage == 0 ? 1 : currPage;
        pageSize = pageSize == null || pageSize == 0 ? 10 : pageSize;
        return new Page<>(currPage, pageSize);
    }
}
