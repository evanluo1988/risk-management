package com.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.springboot.ApplicationTest;
import com.springboot.domain.risk.*;
import com.springboot.mapper.StdLegalCasemedianMapper;
import com.springboot.mapper.StdLegalDataStructuredMapper;
import com.springboot.service.impl.StdLegalServiceImpl;
import com.springboot.vo.risk.LitigaCaseVo;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class StdLegalServiceTest extends ApplicationTest {
    @Autowired
    private StdLegalService stdLegalServiceInSpring;
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

    @Test
    public void testCleanRepeat3(){
        final String reqId = "1b20d84f-3e71-41c6-8430-abd41af63016";
        List<StdLegalDataStructuredTemp> copyS1 = com.google.common.collect.Lists.newArrayList();

        StdLegalDataStructuredTemp s10 = new StdLegalDataStructuredTemp();
        s10.setCaseNo("2");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M6);
        StdLegalDataStructuredTemp s11 = new StdLegalDataStructuredTemp();
        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);

        copyS1.add(s10);
        copyS1.add(s11);

        List<StdLegalEnterpriseExecutedTemp> copyS2 = com.google.common.collect.Lists.newArrayList();

        StdLegalEnterpriseExecutedTemp s21 = new StdLegalEnterpriseExecutedTemp();
        s21.setCaseCode("3");
        s21.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M6);
        StdLegalEnterpriseExecutedTemp s22 = new StdLegalEnterpriseExecutedTemp();
        s22.setCaseCode("3");
        s22.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M6);

        copyS2.add(s21);
        copyS2.add(s22);

        List<StdLegalEntUnexecutedTemp> copyS3 = com.google.common.collect.Lists.newArrayList();

        StdLegalEntUnexecutedTemp s31 = new StdLegalEntUnexecutedTemp();

        s31.setCaseCode("2");
        s31.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M6);
        StdLegalEntUnexecutedTemp s32 = new StdLegalEntUnexecutedTemp();
        s32.setCaseCode("3");
        s32.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M6);

        copyS3.add(s31);
        copyS3.add(s32);

        stdLegalService.cleanRepeat3(copyS1,copyS2,copyS3);

        Assert.assertTrue(copyS1.size()==1 && copyS1.get(0).getCaseRiskLevel()==StdLegalServiceImpl.CASERISKLEVEL_M6
                && copyS2.size()==2
                && copyS3.size()==2);
    }

    @Test
    public void testCleanS1(){
        final String reqId = "1b20d84f-3e71-41c6-8430-abd41af63016";
        List<StdLegalDataStructuredTemp> copyS1 = com.google.common.collect.Lists.newArrayList();
        StdLegalDataStructuredTemp s10 = new StdLegalDataStructuredTemp();
        s10.setCaseNo("1");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s10.setPtype("14");

        StdLegalDataStructuredTemp s11 = new StdLegalDataStructuredTemp();
        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s11.setPtype("15");

        StdLegalDataStructuredTemp s12 = new StdLegalDataStructuredTemp();
        s12.setCaseNo("2");
        s12.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s12.setPtype("16");

        copyS1.add(s10);
        copyS1.add(s11);
        copyS1.add(s12);

        stdLegalService.cleanS1(copyS1);
        Assert.assertTrue("case1 error!",copyS1.size()==1 && copyS1.get(0).getPtype().equals("16"));

        copyS1.clear();
        s10.setCaseNo("1");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s10.setPtype("14");

        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s11.setPtype("15");

        s12.setCaseNo("3");
        s12.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s12.setPtype("15");

        copyS1.add(s10);
        copyS1.add(s11);
        copyS1.add(s12);
        stdLegalService.cleanS1(copyS1);
        Assert.assertTrue("case2 error!",copyS1.size()==1 && copyS1.get(0).getPtype().equals("15"));

        copyS1.clear();
        s10.setCaseNo("1");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s10.setPtype("14");

        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s11.setPtype("15");
        s11.setDocuClass("a");

        s12.setCaseNo("3");
        s12.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s12.setPtype("15");

        copyS1.add(s10);
        copyS1.add(s11);
        copyS1.add(s12);
        stdLegalService.cleanS1(copyS1);
        Assert.assertTrue("case3 error",copyS1.size()==1 && copyS1.get(0).getCaseNo().equals("2"));

        copyS1.clear();
        s10.setCaseNo("1");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s10.setPtype("14");

        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s11.setPtype("15");
        s11.setDocuClass("a");

        s12.setCaseNo("3");
        s12.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s12.setPtype("15");
        s12.setDocuClass("b");

        copyS1.add(s10);
        copyS1.add(s11);
        copyS1.add(s12);
        stdLegalService.cleanS1(copyS1);
        Assert.assertTrue("case4 error",copyS1.size()==2);

        copyS1.clear();
        s10.setCaseNo("1");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s10.setPtype("14");

        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s11.setPtype("15");
        s11.setDocuClass("a");
        s11.setCaseDate(LocalDate.now());

        s12.setCaseNo("3");
        s12.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s12.setPtype("15");
        s12.setDocuClass("a");

        copyS1.add(s10);
        copyS1.add(s11);
        copyS1.add(s12);
        stdLegalService.cleanS1(copyS1);
        Assert.assertTrue("case5 error",copyS1.size()==1 && copyS1.get(0).getCaseNo().equals("2"));

        copyS1.clear();
        s10.setCaseNo("1");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s10.setPtype("14");

        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s11.setPtype("15");
        s11.setDocuClass("a");
        LocalDate now = LocalDate.now();
        s11.setCaseDate(now);

        s12.setCaseNo("3");
        s12.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s12.setPtype("15");
        s12.setDocuClass("a");
        s12.setCaseDate(now);

        copyS1.add(s10);
        copyS1.add(s11);
        copyS1.add(s12);
        stdLegalService.cleanS1(copyS1);
        Assert.assertTrue("case6 error",copyS1.size()==1);

        copyS1.clear();
        s10.setCaseNo("1");
        s10.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s10.setPtype("14");

        s11.setCaseNo("2");
        s11.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s11.setPtype("15");
        s11.setDocuClass("a");
        s11.setCaseDate(now);

        s12.setCaseNo("3");
        s12.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s12.setPtype("15");
        s12.setDocuClass("a");
        s12.setCaseDate(now.plusDays(1));

        copyS1.add(s10);
        copyS1.add(s11);
        copyS1.add(s12);
        stdLegalService.cleanS1(copyS1);
        Assert.assertTrue("case7 error",copyS1.size()==2);
    }

    @Test
    public void testCleanS2(){
        List<StdLegalEnterpriseExecutedTemp> copyS2 = com.google.common.collect.Lists.newArrayList();

        StdLegalEnterpriseExecutedTemp s21 = new StdLegalEnterpriseExecutedTemp();
        s21.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s21.setCaseCode("1");
        StdLegalEnterpriseExecutedTemp s22 = new StdLegalEnterpriseExecutedTemp();
        s22.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s22.setCaseCode("3");
        StdLegalEnterpriseExecutedTemp s23 = new StdLegalEnterpriseExecutedTemp();
        s23.setCaseRiskLevel(StdLegalServiceImpl.CASERISKLEVEL_M3);
        s23.setCaseCode("3");

        copyS2.add(s21);
        copyS2.add(s22);
        copyS2.add(s23);

        stdLegalService.clearS2(copyS2);
        Assert.assertTrue(copyS2.size()==1);

        copyS2.clear();
        s21.setCaseCreateTime(LocalDate.now());
        copyS2.add(s21);
        copyS2.add(s22);
        copyS2.add(s23);
        stdLegalService.clearS2(copyS2);
        Assert.assertTrue(copyS2.size()==1);

        copyS2.clear();
        s21.setCaseCreateTime(LocalDate.now());
        s22.setCaseCreateTime(LocalDate.now());
        copyS2.add(s21);
        copyS2.add(s22);
        copyS2.add(s23);
        stdLegalService.clearS2(copyS2);
        Assert.assertTrue(copyS2.size()==2);
    }

    @Test
    public void testPreStdSsDatas(){
        final String reqId = "1b20d84f-3e71-41c6-8430-abd41af63016";
        stdLegalServiceInSpring.preStdSsDatas(reqId);
    }

    @Test
    public void testLitigaCaseVo(){
        final String reqId = "1b20d84f-3e71-41c6-8430-abd41af63016";
        List<LitigaCaseVo> litigaCase = stdLegalServiceInSpring.getLitigaCase(reqId);
        System.out.println(litigaCase);
    }
}
