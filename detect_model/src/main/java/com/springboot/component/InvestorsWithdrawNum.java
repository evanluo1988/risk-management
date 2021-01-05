package com.springboot.component;

import com.springboot.domain.StdEntAlter;
import com.springboot.mapper.StdEntAlterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("InvestorsWithdrawNum")
public class InvestorsWithdrawNum implements QuotaComponent{

    @Autowired
    private StdEntAlterMapper stdEntAlterMapper;

    @Override
    public String execQuota(String reqId) {

        //distinct(变更前内容【ALTBE】、变更后内容【ALTAF】、变更日期【ALTDATE】)if{月化(【@当前时间】-变更日期【ALTDATE】)<=12 &【变更事项ALTITEM】='21'}
        List<StdEntAlter> stdEntAlterList = stdEntAlterMapper.findInvestorWithdrawList(reqId);
        int altCount = 0;
        String before = "";
        String after = "";
        Set<String> GDSet = new HashSet<>();// 股东列表
        for(StdEntAlter stdEntAlter : stdEntAlterList) {
            before = stdEntAlter.getAltbe();
            after = stdEntAlter.getAltaf();
            GDSet.clear();
            // 1、 先提取变更前股东名称放入集合
            getEntName(GDSet, before, true);
            if (!CollectionUtils.isEmpty(GDSet)) {
                // 2、再删除变更后股东
                getEntName(GDSet, after, false);
            }
            altCount += GDSet.size();// 加上每一个变更的减少人数
        }
        return String.valueOf(altCount);
    }

    //提取股东名字
    private void getEntName(Set<String> gdSet, String gdStr, boolean b) {
        if (StringUtils.isEmpty(gdStr)) {
            return;
        }

        Set<String> guDongSet = getNames(gdStr);
        for (String gdName : guDongSet) {
            if (b) {
                gdSet.add(gdName.replaceAll("[^\u4E00-\u9FA5]", ""));// 变更前股东人数
            } else {
                gdSet.remove(gdName.replaceAll("[^\u4E00-\u9FA5]", ""));// 删除每一个在变更后内出现的股东名称，余下的就是变更后没有的股东名称
            }
        }
    }

    public Set<String> getNames(String str) {
        Set<String> set = new HashSet<>();

        if(StringUtils.isEmpty(str)){
            return set;
        }
        String[] names = {};
        Pattern regGetName = Pattern.compile("[\u4e00-\u9fa5]+(?=认缴)");
        String[] nameArr1 = {"企业名称", "姓名"};
        String[] nameArr2 = {"法人股东", "自然人股东"};
        if(str.contains("|")){
            names = str.split("\\|");
            for (String name : names) {
                Matcher matcherStr = regGetName.matcher(name);
                while (matcherStr.find()) {
                    set.add(matcherStr.group().replaceAll(" ", ""));
                }
            }
        } else if(isContains(Arrays.asList(nameArr1),str)){
            regGetName = Pattern.compile("(?<=(姓名|企业名称)(:|：))[\u4e00-\u9fa5]+(?=(;|；))");
            Matcher matcherStr = regGetName.matcher(str);
            while (matcherStr.find()) {
                set.add(matcherStr.group().replaceAll(" ", ""));
            }
        } else if (isContains(Arrays.asList(nameArr2),str)){
            regGetName = Pattern.compile("(?<=(法人股东|自然人股东)(:|：)|(\\d+))[\u4e00-\u9fa5]+(?=(法人股东|自然人股东)(:|：)|\\d+)");
            Matcher matcherStr = regGetName.matcher(str);
            while (matcherStr.find()) {
                set.add(matcherStr.group().replaceAll(" ", ""));
            }
        }
        set.remove("");
        set.remove(" ");
        return set;
    }

    // 判断字符串中是否包含List中任意元素
    public boolean isContains(List<String> list, String str){
        for (String name : list) {
            if(str.contains(name)){
                return true;
            }
        }
        return false;
    }

}
