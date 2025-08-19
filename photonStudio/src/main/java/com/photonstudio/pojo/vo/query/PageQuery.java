package com.photonstudio.pojo.vo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 分页查询参数
 *
 * @author bingo
 */
@Data
public class PageQuery {
    /** 当前页 */
    @NotNull(message = "当前页不能为空")
    @Max(value = 500, message = "当前页不能超过500页")
    private Integer current;
    /** 每页条数 */
    @NotNull(message = "每页条数不能为空")
    @Max(value = 1000, message = "每页条数不能超过1000条")
    private Integer size;

    /**
     * 转换为MybatisPlus分页对象
     *
     * @return MybatisPlus分页对象
     */
    public <T> Page<T> toPage() {
        return new Page<>(current, size);
    }
}
