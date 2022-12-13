package com.fly.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应类
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -3452602113181606415L;

    public List<T> list;
    public Integer total;

    public PageResult(List<T> list, long total) {
        this.list = list;
        this.total = (int) total;
    }

    public static <T> PageResult of(List<T> list, Integer total) {
        return new PageResult(list, total == null ? 0 : total);
    }

    public static <T> PageResult of(List<T> list, Long total) {
        return new PageResult(list, total == null ? 0 : total);
    }
}
