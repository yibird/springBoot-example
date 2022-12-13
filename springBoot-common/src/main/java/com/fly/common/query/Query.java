package com.fly.common.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 通用查询参数类
 */
@Data
public class Query {
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    public Integer page;

    @NotNull(message = "每页条数不能为空")
    @Range(min = 1, max = 1000, message = "每页条数，取值范围 1-1000")
    public Integer limit;

    /**
     * 排序字段,多个字段以,号分割
     */
    public String order;
    /**
     * 是否升序
     */
    public boolean asc;

    public void setPage(Integer page) {
        this.page = ObjectUtils.isEmpty(this.limit) ? page : (page - 1) * this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
        this.page = ObjectUtils.isEmpty(this.page) ? this.page : (this.page - 1) * this.limit;
    }
}
