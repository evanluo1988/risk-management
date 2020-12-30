package com.springboot.component;

import com.springboot.ApplicationTest;
import com.springboot.domain.risk.StdEntBasic;
import com.springboot.domain.risk.StdIaPartent;
import com.springboot.service.StdEntBasicService;
import com.springboot.service.StdIaPartentService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class InventorNumTest extends ApplicationTest {
    @InjectMocks
    private InventorNum inventorNum;
    @Mock
    private StdIaPartentService stdIaPartentService;
    @Mock
    private StdEntBasicService stdEntBasicService;

    @Test
    public void testExecQuota() {
        String reqId = "123";
        StdEntBasic stdEntBasic = new StdEntBasic();
        stdEntBasic.setEntName("科创汇捷");
        Mockito.when(stdEntBasicService.getStdEntBasicByReqId(reqId)).thenReturn(stdEntBasic);

        List<StdIaPartent> stdIaPartentList = Lists.newArrayList();
        StdIaPartent stdIaPartent1 = new StdIaPartent();
        stdIaPartent1.setApc("科创汇捷123");
        stdIaPartent1.setIno("张三;李四");
        StdIaPartent stdIaPartent2 = new StdIaPartent();
        stdIaPartent2.setApc("科创");
        stdIaPartent2.setIno("王五");
        stdIaPartentList.add(stdIaPartent1);
        stdIaPartentList.add(stdIaPartent2);

        Mockito.when(stdIaPartentService.findByReqId(reqId)).thenReturn(stdIaPartentList);
        String res = inventorNum.execQuota(reqId);
        Assert.assertEquals("2", res);

        StdIaPartent stdIaPartent3 = new StdIaPartent();
        stdIaPartent3.setApc("科创汇捷123");
        stdIaPartent3.setIno("");
        stdIaPartentList.add(stdIaPartent3);
        res = inventorNum.execQuota(reqId);
        Assert.assertEquals("2", res);

        stdIaPartent3.setIno("张三;王五");
        res = inventorNum.execQuota(reqId);
        Assert.assertEquals("1", res);

    }

}
