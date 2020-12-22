package com.springboot.component;

import com.google.common.collect.Lists;
import com.springboot.ApplicationTest;
import com.springboot.mapper.StdLegalDataStructuredTempMapper;
import com.springboot.model.LitigaInitiativeModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class LitigaInitiativeTest extends ApplicationTest {
    @InjectMocks
    private LitigaInitiative litigaInitiative;
    @Mock
    private StdLegalDataStructuredTempMapper stdLegalDataStructuredTempMapper;

    @Test
    public void testExecQuota() {
        String reqId = "123";
        Mockito.when(stdLegalDataStructuredTempMapper.getLitigaInitiativeResult(reqId)).thenReturn(null);
        String res = litigaInitiative.execQuota(reqId);
        Assert.assertNull(res);

        List<LitigaInitiativeModel> litigaInitiativeModelList = Lists.newArrayList();
        LitigaInitiativeModel m1 = new LitigaInitiativeModel();
        m1.setId(1L);
        m1.setPhase("一审");
        m1.setReqId(reqId);
        litigaInitiativeModelList.add(m1);
        LitigaInitiativeModel m2 = new LitigaInitiativeModel();
        m2.setId(2L);
        m2.setPhase("一审");
        m2.setReqId(reqId);
        litigaInitiativeModelList.add(m2);
        Mockito.when(stdLegalDataStructuredTempMapper.getLitigaInitiativeResult(reqId)).thenReturn(litigaInitiativeModelList);
        res = litigaInitiative.execQuota(reqId);
        Assert.assertEquals("else", res);

        LitigaInitiativeModel p1 = new LitigaInitiativeModel();
        p1.setId(3L);
        p1.setPhase("一审");
        p1.setReqId(reqId);
        p1.setPlaintiff("evan");
        litigaInitiativeModelList.add(p1);
        res = litigaInitiative.execQuota(reqId);
        Assert.assertEquals("偏被动", res);

    }

}
