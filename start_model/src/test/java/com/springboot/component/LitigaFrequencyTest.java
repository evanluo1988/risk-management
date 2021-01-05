package com.springboot.component;

import com.springboot.ApplicationTest;
import com.springboot.domain.StdLegalDataStructuredTemp;
import com.springboot.mapper.StdLegalDataStructuredTempMapper;
import com.springboot.mapper.StdLegalEntUnexecutedTempMapper;
import com.springboot.mapper.StdLegalEnterpriseExecutedTempMapper;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

public class LitigaFrequencyTest extends ApplicationTest {
    @InjectMocks
    private LitigaFrequency litigaFrequency;

    @Mock
    private StdLegalDataStructuredTempMapper stdLegalDataStructuredTempMapper;
    @Mock
    private StdLegalEnterpriseExecutedTempMapper stdLegalEnterpriseExecutedTempMapper;
    @Mock
    private StdLegalEntUnexecutedTempMapper stdLegalEntUnexecutedTempMapper;

    @Test
    public void testExecQuota() {
        String reqId = "123";
        Mockito.when(stdLegalDataStructuredTempMapper.selectByMap(Mockito.anyMap())).thenReturn(null);
        Mockito.when(stdLegalEnterpriseExecutedTempMapper.selectByMap(Mockito.anyMap())).thenReturn(null);
        Mockito.when(stdLegalEntUnexecutedTempMapper.selectByMap(Mockito.anyMap())).thenReturn(null);
        String res = litigaFrequency.execQuota(reqId);
        Assert.assertEquals("else", res);

        List<StdLegalDataStructuredTemp> stdLegalDataStructuredTempList = Lists.newArrayList();
        StdLegalDataStructuredTemp temp1 = new StdLegalDataStructuredTemp();
        temp1.setCaseDate(LocalDate.now());
        StdLegalDataStructuredTemp temp2 = new StdLegalDataStructuredTemp();
        temp2.setPdate(LocalDate.of(2019,5,1));
        StdLegalDataStructuredTemp temp3 = new StdLegalDataStructuredTemp();
        temp3.setCaseNo("（2019）沪0115民初44415-533159235号");
        StdLegalDataStructuredTemp temp4 = new StdLegalDataStructuredTemp();

        stdLegalDataStructuredTempList.add(temp1);
        stdLegalDataStructuredTempList.add(temp2);
        stdLegalDataStructuredTempList.add(temp3);
        stdLegalDataStructuredTempList.add(temp4);
        Mockito.when(stdLegalDataStructuredTempMapper.selectByMap(Mockito.anyMap())).thenReturn(stdLegalDataStructuredTempList);
        res = litigaFrequency.execQuota(reqId);
        Assert.assertEquals("else", res);

        StdLegalDataStructuredTemp temp5 = new StdLegalDataStructuredTemp();
        temp5.setCaseNo("（2018）沪0115民初44415-533159235号");
        stdLegalDataStructuredTempList.add(temp5);
        res = litigaFrequency.execQuota(reqId);
        Assert.assertEquals("频繁", res);

    }

}
