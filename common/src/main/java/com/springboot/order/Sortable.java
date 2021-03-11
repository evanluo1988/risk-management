package com.springboot.order;

import com.alibaba.druid.sql.ast.SQLOrderingSpecification;
import com.springboot.exception.ServiceException;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author lhf
 * @date 2021/3/10 6:19 下午
 **/
@Data
public class Sortable {

    private String orderColumn;
    private SQLOrderingSpecification orderingSpecification;

    public void checkColumn(Class clazz){
        if (StringUtils.isEmpty(orderColumn) || Objects.isNull(orderingSpecification)){
            return ;
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        final boolean match = Arrays.asList(declaredFields).stream().map(Field::getName).anyMatch(item -> item.equals(this.orderColumn));
        if (!match){
            throw new ServiceException("不正确的排序列参数");
        }
    }
}
