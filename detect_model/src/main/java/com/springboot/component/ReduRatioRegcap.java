package com.springboot.component;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.springboot.domain.StdEntAlter;
import com.springboot.mapper.StdEntAlterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.constant.GlobalConstants.INF;
import static java.util.regex.Pattern.*;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 16:48
 * @Version 1.0
 */
@Component("ReduRatioRegcap")
public class ReduRatioRegcap implements QuotaComponent {

    @Autowired
    private StdEntAlterMapper stdEntAlterMapper;

    @Override
    public String execQuota(String reqId) {
        /**
         * "1.筛选注册资本变更的记录：
         * distinct(变更前内容【ALTBE】、变更后内容【ALTAF】、变更日期【ALTDATE】)if{月化(【@当前时间】-变更日期【ALTDATE】)<=12 &变更事项【ALTITEM】=05}
         */
        List<StdEntAlter> stdEntAlters = stdEntAlterMapper.findReduRatioRegcapList(reqId);
        if (CollectionUtils.isEmpty(stdEntAlters)) {
            return null;
        }

        /**
         * 2.在第1点取数范围内，取近12月最早的一条变更记录与最近的一条变更记录
         * ①获取 变更前内容【ALTBE】 if{ min(变更日期【ALTDATE】)}
         * ②获取 变更后内容【ALTAF】if{ max(变更日期【ALTDATE】)}
         * ③求注册资本减少的比例：
         * 提取字段中的数字，再执行公式：
         * (①变更前内容【ALTBE】提取注册资本的数字 - ②变更后内容【ALTAF】提取注册资本的数字）/①变更前内容【ALTBE】提取注册资本的数字*100%
         */
        List<StdEntAlter> sortedStdEntAlters = stdEntAlters
                .stream()
                .sorted(Comparator.comparing(s -> LocalDate.parse(s.getAltdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .collect(Collectors.toList());

        StdEntAlter min = sortedStdEntAlters.get(0);
        StdEntAlter max = sortedStdEntAlters.get(sortedStdEntAlters.size() - 1);

        String altbe = min.getAltbe();
        String altaf = max.getAltaf();

        String altbeStr = getNumber(altbe);
        String altafStr = getNumber(altaf);

        if (StringUtils.isBlank(altbeStr) || StringUtils.isBlank(altafStr)) {
            return null;
        }

        BigDecimal altbeNum = new BigDecimal(altbeStr);
        BigDecimal altafNum = new BigDecimal(altafStr);

        if (altbeNum.equals(BigDecimal.ZERO)) {
            double subtract = altafNum.multiply(new BigDecimal("-1")).subtract(BigDecimal.ZERO).doubleValue();
            if (subtract > 0) {
                return INF;
            } else if (subtract < 0) {
                return "-" + INF;
            } else if (subtract == 0) {
                return null;
            }
        }

        BigDecimal result = (altbeNum.subtract(altafNum)).divide(altbeNum, 2, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(result.doubleValue());
    }

    /**
     * 2019/3/28
     *
     * @Description: 字符串提取数字
     * @Author: YangXuePing
     * @Department: WY技术和创新中心-重庆研发部
     */
    private static String getNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String REGEX = "[^(0-9).]";
        return compile(REGEX).matcher(str).replaceAll("").trim();
    }
}
