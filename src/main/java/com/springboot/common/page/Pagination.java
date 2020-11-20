package com.springboot.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @Author 刘宏飞
 * @Date 2020/11/20 17:06
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
    /**
     * 总记录数
     */
    private Long count;
    /**
     * 记录
     */
    private Collection<T> data;

    public static<T> Pagination<T> of(IPage<T> entityIPage) {
        Pagination<T> tPagination = new Pagination<>();
        tPagination.setCount(entityIPage.getTotal());
        tPagination.setData(entityIPage.getRecords());
        return tPagination;
    }
}
