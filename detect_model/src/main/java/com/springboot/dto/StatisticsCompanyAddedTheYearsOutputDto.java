package com.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhf
 * @date 2021/3/3 10:57 上午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsCompanyAddedTheYearsOutputDto {
    /**
     * 年，-1标识以前
     */
    private Integer yearName;
    /**
     * 增长数
     */
    private Integer incrementNum;
}
