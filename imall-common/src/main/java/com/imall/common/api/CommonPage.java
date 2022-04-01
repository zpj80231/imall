package com.imall.common.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CommonPage
 * @Description MyBatis Plus分页数据封装
 * @Version 1.0
 * @Date 2020/9/30 16:41
 * @Created by zpj80231
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

    public static <T> CommonPage<T> restPage(Page<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(pageResult.getCurrent());
        result.setPageSize(pageResult.getSize());
        result.setTotal(pageResult.getTotal());
        result.setTotalPage(pageResult.getTotal()/pageResult.getSize()+1);
        result.setList(pageResult.getRecords());
        return result;
    }
}
