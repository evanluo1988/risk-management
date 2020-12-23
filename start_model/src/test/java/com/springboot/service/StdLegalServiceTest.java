package com.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.ApplicationTest;
import com.springboot.domain.risk.*;
import com.springboot.mapper.StdLegalCasemedianMapper;
import com.springboot.mapper.StdLegalDataStructuredMapper;
import com.springboot.service.impl.StdLegalServiceImpl;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StdLegalServiceTest extends ApplicationTest {
    @InjectMocks
    private StdLegalServiceImpl stdLegalService;

    @Mock
    private StdLegalDataStructuredService stdLegalDataStructuredService;
    @Mock
    private StdLegalEnterpriseExecutedService stdLegalEnterpriseExecutedService;
    @Mock
    private StdLegalEntUnexecutedService stdLegalEntUnexecutedService;
    @Mock
    private StdLegalDataStructuredMapper stdLegalDataStructuredMapper;
    @Mock
    private StdLegalCasemedianMapper stdLegalCasemedianMapper;

    @Test
    public void testCreateStdLegalMidTable() {
        List<StdLegalDataStructured> stdLegalDataStructuredList = Lists.newArrayList();
        Mockito.when(stdLegalDataStructuredService.findStdLegalDataStructuredByReqId("1b20d84f-3e71-41c6-8430-abd41af63016")).thenReturn(stdLegalDataStructuredList);
        stdLegalService.createStdLegalMidTable("1b20d84f-3e71-41c6-8430-abd41af63016");
        Mockito.verify(stdLegalDataStructuredService, Mockito.times(1)).findStdLegalDataStructuredByReqId("1b20d84f-3e71-41c6-8430-abd41af63016");

        StdLegalDataStructured s1 = new StdLegalDataStructured();
        stdLegalDataStructuredList.add(s1);
        stdLegalService.createStdLegalMidTable("1b20d84f-3e71-41c6-8430-abd41af63016");
    }


    @Test
    public void testCleanRepeat2(){
        final String reqId = "1b20d84f-3e71-41c6-8430-abd41af63016";
        List<StdLegalDataStructuredTemp> copyS1 = com.google.common.collect.Lists.newArrayList();
        StdLegalDataStructuredTemp s10 = new StdLegalDataStructuredTemp();
        s10.setCaseNo("1");

        StdLegalDataStructuredTemp s11 = new StdLegalDataStructuredTemp();
        s11.setCaseNo("2");
        copyS1.add(s10);
        copyS1.add(s11);

        List<StdLegalEnterpriseExecutedTemp> copyS2 = com.google.common.collect.Lists.newArrayList();
        StdLegalEnterpriseExecutedTemp s21 = new StdLegalEnterpriseExecutedTemp();
        s21.setCaseCode("1");

        StdLegalEnterpriseExecutedTemp s22 = new StdLegalEnterpriseExecutedTemp();
        s22.setCaseCode("3");
        copyS2.add(s21);
        copyS2.add(s22);

        List<StdLegalEntUnexecutedTemp> copyS3 = com.google.common.collect.Lists.newArrayList();
        StdLegalEntUnexecutedTemp s31 = new StdLegalEntUnexecutedTemp();
        s31.setCaseCode("2");
        StdLegalEntUnexecutedTemp s32 = new StdLegalEntUnexecutedTemp();
        s32.setCaseCode("3");

        copyS3.add(s31);
        copyS3.add(s32);

        stdLegalService.cleanRepeat2(copyS1,copyS2,copyS3);

        /**
         * s1		1
         * s2
         * s3		2	3
         */
        Assert.assertTrue(copyS1.size()==1 && copyS2.size()==0 && copyS3.size()==2);
    }

    public static void main(String[] args) {
        List<StdLegalDataStructuredTemp> copyS1 = com.google.common.collect.Lists.newArrayList();
        StdLegalDataStructuredTemp s10 = new StdLegalDataStructuredTemp();
        s10.setCaseNo("1");

        StdLegalDataStructuredTemp s11 = new StdLegalDataStructuredTemp();
        s11.setCaseNo("2");
        copyS1.add(s10);
        copyS1.add(s11);

        List<StdLegalEnterpriseExecutedTemp> copyS2 = com.google.common.collect.Lists.newArrayList();
        StdLegalEnterpriseExecutedTemp s21 = new StdLegalEnterpriseExecutedTemp();
        s21.setCaseCode("1");

        StdLegalEnterpriseExecutedTemp s22 = new StdLegalEnterpriseExecutedTemp();
        s22.setCaseCode("3");
        copyS2.add(s21);
        copyS2.add(s22);

        List<StdLegalEnterpriseExecutedTemp> copyS23 = com.google.common.collect.Lists.newArrayList();
        copyS23.add(s22);

        copyS2.removeAll(copyS23);
        System.out.println(copyS2);
    }

    @Test
    public void testPreStdSsDatas(){
        final String reqId = "1b20d84f-3e71-41c6-8430-abd41af63016";
        Mockito.when(stdLegalDataStructuredService.findStdLegalDataStructuredByReqId(reqId))
                .thenReturn(initS1());
        Mockito.when(stdLegalEnterpriseExecutedService.findStdLegalEnterpriseExecutedByReqId(reqId))
                .thenReturn(initS2());
        Mockito.when(stdLegalEntUnexecutedService.findStdLegalEntUnexecutedByReqId(reqId))
                .thenReturn(initS3());

        Mockito.when(stdLegalCasemedianMapper.selectList(Mockito.any(LambdaQueryWrapper.class))).thenReturn(initCasemedian());

        stdLegalService.preStdSsDatas(reqId);

    }

    private List<StdLegalCasemedian> initCasemedian() {
        List<StdLegalCasemedian> stdLegalCasemedians = Lists.newArrayList();

//        StdLegalCasemedian stdLegalCasemedian1 = new StdLegalCasemedian();
//        stdLegalCasemedian1.setId(1L);
//        stdLegalCasemedian1.setSerialNo("2");
//        stdLegalCasemedian1.setCaseRiskLevel(StdLegalServiceImpl.CASE_RISK_LEVEL_STAGE.get(0));

        StdLegalCasemedian stdLegalCasemedian2 = new StdLegalCasemedian();
        stdLegalCasemedian2.setId(2L);
        stdLegalCasemedian2.setSerialNo("3");
        stdLegalCasemedian2.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_H);

        StdLegalCasemedian stdLegalCasemedian3 = new StdLegalCasemedian();
        stdLegalCasemedian3.setId(3L);
        stdLegalCasemedian3.setSerialNo("11");
        stdLegalCasemedian3.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M6);

        StdLegalCasemedian stdLegalCasemedian4 = new StdLegalCasemedian();
        stdLegalCasemedian4.setId(4L);
        stdLegalCasemedian4.setSerialNo("12");
        stdLegalCasemedian4.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);

        StdLegalCasemedian stdLegalCasemedian5 = new StdLegalCasemedian();
        stdLegalCasemedian5.setId(4L);
        stdLegalCasemedian5.setSerialNo("100");
        stdLegalCasemedian5.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M4);

        StdLegalCasemedian stdLegalCasemedian6 = new StdLegalCasemedian();
        stdLegalCasemedian6.setId(4L);
        stdLegalCasemedian6.setSerialNo("101");
        stdLegalCasemedian6.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M4);

        StdLegalCasemedian stdLegalCasemedian7 = new StdLegalCasemedian();
        stdLegalCasemedian7.setId(4L);
        stdLegalCasemedian7.setSerialNo("102");
        stdLegalCasemedian7.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);

        StdLegalCasemedian stdLegalCasemedian8 = new StdLegalCasemedian();
        stdLegalCasemedian8.setId(4L);
        stdLegalCasemedian8.setSerialNo("103");
        stdLegalCasemedian8.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_H);

        StdLegalCasemedian stdLegalCasemedian9 = new StdLegalCasemedian();
        stdLegalCasemedian9.setId(4L);
        stdLegalCasemedian9.setSerialNo("104");
        stdLegalCasemedian9.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M6);

        StdLegalCasemedian stdLegalCasemedian10 = new StdLegalCasemedian();
        stdLegalCasemedian10.setId(4L);
        stdLegalCasemedian10.setSerialNo("105");
        stdLegalCasemedian10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_H);

        StdLegalCasemedian stdLegalCasemedian11 = new StdLegalCasemedian();
        stdLegalCasemedian11.setId(4L);
        stdLegalCasemedian11.setSerialNo("106");
        stdLegalCasemedian11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_L);

//        StdLegalCasemedian stdLegalCasemedian5 = new StdLegalCasemedian();
//        stdLegalCasemedian5.setId(5L);
//        stdLegalCasemedian5.setSerialNo("12");
//        stdLegalCasemedian5.setCaseRiskLevel(StdLegalServiceImpl.CASE_RISK_LEVEL_STAGE.get(2));

//        stdLegalCasemedians.add(stdLegalCasemedian1);
        stdLegalCasemedians.add(stdLegalCasemedian2);
        stdLegalCasemedians.add(stdLegalCasemedian3);
        stdLegalCasemedians.add(stdLegalCasemedian4);
        stdLegalCasemedians.add(stdLegalCasemedian5);
        stdLegalCasemedians.add(stdLegalCasemedian6);
        stdLegalCasemedians.add(stdLegalCasemedian7);
        stdLegalCasemedians.add(stdLegalCasemedian8);
        stdLegalCasemedians.add(stdLegalCasemedian9);
        stdLegalCasemedians.add(stdLegalCasemedian10);
        stdLegalCasemedians.add(stdLegalCasemedian11);
//        stdLegalCasemedians.add(stdLegalCasemedian5);
        return stdLegalCasemedians;
    }

    private List<StdLegalEntUnexecuted> initS3() {
        List<StdLegalEntUnexecuted> s3 = Lists.newArrayList();

        StdLegalEntUnexecuted stdLegalEntUnexecuted1 = new StdLegalEntUnexecuted();
        stdLegalEntUnexecuted1.setId(31L);
        stdLegalEntUnexecuted1.setCaseCode("1");

        StdLegalEntUnexecuted stdLegalEntUnexecuted5 = new StdLegalEntUnexecuted();
        stdLegalEntUnexecuted5.setId(32L);
        stdLegalEntUnexecuted5.setCaseCode("2");

        StdLegalEntUnexecuted stdLegalEntUnexecuted31 = new StdLegalEntUnexecuted();
        stdLegalEntUnexecuted31.setId(33L);
        //stdLegalEntUnexecuted31.setCaseCode("31");

        StdLegalEntUnexecuted stdLegalEntUnexecuted32 = new StdLegalEntUnexecuted();
        stdLegalEntUnexecuted32.setId(34L);
        stdLegalEntUnexecuted32.setCaseCode("23");

        StdLegalEntUnexecuted stdLegalEntUnexecuted33 = new StdLegalEntUnexecuted();
        stdLegalEntUnexecuted33.setId(35L);
        stdLegalEntUnexecuted33.setCaseCode("23");

        s3.add(stdLegalEntUnexecuted1);
        s3.add(stdLegalEntUnexecuted5);
        s3.add(stdLegalEntUnexecuted31);
        s3.add(stdLegalEntUnexecuted32);
        s3.add(stdLegalEntUnexecuted33);
        return s3;
    }

    private List<StdLegalEnterpriseExecuted> initS2() {
        List<StdLegalEnterpriseExecuted> s2 = Lists.newArrayList();

        StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted1 = new StdLegalEnterpriseExecuted();
        stdLegalEnterpriseExecuted1.setId(21L);
        stdLegalEnterpriseExecuted1.setCaseCode("1");

        StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted2 = new StdLegalEnterpriseExecuted();
        stdLegalEnterpriseExecuted2.setId(22L);
        //stdLegalEnterpriseExecuted2.setCaseCode("2");

        StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted21 = new StdLegalEnterpriseExecuted();
        stdLegalEnterpriseExecuted21.setId(23L);
        stdLegalEnterpriseExecuted21.setCaseCode("3");

        StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted22 = new StdLegalEnterpriseExecuted();
        stdLegalEnterpriseExecuted22.setId(24L);
        stdLegalEnterpriseExecuted22.setCaseCode("22");
        stdLegalEnterpriseExecuted22.setCaseCreateTime(LocalDate.now());

        StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted23 = new StdLegalEnterpriseExecuted();
        stdLegalEnterpriseExecuted23.setId(25L);
        stdLegalEnterpriseExecuted23.setCaseCode("12");
        stdLegalEnterpriseExecuted23.setCaseCreateTime(LocalDate.now().plusYears(-100));

        s2.add(stdLegalEnterpriseExecuted1);
        s2.add(stdLegalEnterpriseExecuted2);
        s2.add(stdLegalEnterpriseExecuted21);
        s2.add(stdLegalEnterpriseExecuted22);
        s2.add(stdLegalEnterpriseExecuted23);
        return s2;
    }

    private List<StdLegalDataStructured> initS1() {
        ArrayList<StdLegalDataStructured> s1 = Lists.newArrayList();

        StdLegalDataStructured stdLegalDataStructured1 = new StdLegalDataStructured();
        stdLegalDataStructured1.setId(11L);
        //stdLegalDataStructured1.setCaseNo("1");
        stdLegalDataStructured1.setSerialno("1");
        stdLegalDataStructured1.setPtype("15");

        StdLegalDataStructured stdLegalDataStructured2 = new StdLegalDataStructured();
        stdLegalDataStructured2.setId(12L);
        stdLegalDataStructured2.setCaseNo("2");
        stdLegalDataStructured2.setSerialno("2");
        stdLegalDataStructured2.setPtype("15");

        StdLegalDataStructured stdLegalDataStructured3 = new StdLegalDataStructured();
        stdLegalDataStructured3.setId(13L);
        stdLegalDataStructured3.setCaseNo("3");
        stdLegalDataStructured3.setSerialno("3");
        stdLegalDataStructured3.setPtype("16");

        StdLegalDataStructured stdLegalDataStructured11 = new StdLegalDataStructured();
        stdLegalDataStructured11.setId(14L);
        stdLegalDataStructured11.setCaseNo("21");
        stdLegalDataStructured11.setSerialno("11");
        stdLegalDataStructured11.setPtype("14");

        StdLegalDataStructured stdLegalDataStructured12 = new StdLegalDataStructured();
        stdLegalDataStructured12.setId(15L);
        stdLegalDataStructured12.setCaseNo("21");
        stdLegalDataStructured12.setSerialno("12");
        stdLegalDataStructured12.setPtype("6");

        StdLegalDataStructured stdLegalDataStructured13 = new StdLegalDataStructured();
        stdLegalDataStructured13.setId(15L);
        stdLegalDataStructured13.setCaseNo("100");
        stdLegalDataStructured13.setSerialno("100");
        stdLegalDataStructured13.setPtype("55");

        StdLegalDataStructured stdLegalDataStructured14 = new StdLegalDataStructured();
        stdLegalDataStructured14.setId(15L);
        stdLegalDataStructured14.setCaseNo("101");
        stdLegalDataStructured14.setSerialno("101");
        stdLegalDataStructured14.setPtype("13");

        StdLegalDataStructured stdLegalDataStructured15 = new StdLegalDataStructured();
        stdLegalDataStructured15.setId(15L);
        stdLegalDataStructured15.setCaseNo("102");
        stdLegalDataStructured15.setSerialno("102");
        stdLegalDataStructured15.setPtype("16");
        stdLegalDataStructured15.setDocuClass("docuclass");

        StdLegalDataStructured stdLegalDataStructured16 = new StdLegalDataStructured();
        stdLegalDataStructured16.setId(15L);
        stdLegalDataStructured16.setCaseNo("103");
        stdLegalDataStructured16.setSerialno("103");
        stdLegalDataStructured16.setPtype("15");

        StdLegalDataStructured stdLegalDataStructured17 = new StdLegalDataStructured();
        stdLegalDataStructured17.setId(15L);
        stdLegalDataStructured17.setCaseNo("104");
        stdLegalDataStructured17.setSerialno("104");
        stdLegalDataStructured17.setPtype("16");
        stdLegalDataStructured17.setDocuClass("docuclass");

        StdLegalDataStructured stdLegalDataStructured18 = new StdLegalDataStructured();
        stdLegalDataStructured18.setId(15L);
        stdLegalDataStructured18.setCaseNo("105");
        stdLegalDataStructured18.setSerialno("105");
        stdLegalDataStructured18.setPtype("14");

        StdLegalDataStructured stdLegalDataStructured19 = new StdLegalDataStructured();
        stdLegalDataStructured19.setId(15L);
        stdLegalDataStructured19.setCaseNo("106");
        stdLegalDataStructured19.setSerialno("106");
        stdLegalDataStructured19.setPtype("2");

        s1.add(stdLegalDataStructured1);
        s1.add(stdLegalDataStructured2);
        s1.add(stdLegalDataStructured3);
        s1.add(stdLegalDataStructured11);
        s1.add(stdLegalDataStructured12);
        s1.add(stdLegalDataStructured13);
        s1.add(stdLegalDataStructured14);
        s1.add(stdLegalDataStructured15);
        s1.add(stdLegalDataStructured16);
        s1.add(stdLegalDataStructured17);
        s1.add(stdLegalDataStructured18);
        s1.add(stdLegalDataStructured19);
        return s1;
    }
}
