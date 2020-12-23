package com.springboot.component;

import com.springboot.ApplicationTest;
import com.springboot.mapper.StdLegalDataStructuredTempMapper;
import com.springboot.model.VerdictResultModel;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class TendencyJudgResultTest extends ApplicationTest {
    @InjectMocks
    private TendencyJudgResult tendencyJudgResult;
    @Mock
    private StdLegalDataStructuredTempMapper stdLegalDataStructuredTempMapper;

    @Test
    public void testExecQuota() {
        String reqId = "123";
        Mockito.when(stdLegalDataStructuredTempMapper.getVerdictResult(reqId)).thenReturn(null);
        String res = tendencyJudgResult.execQuota(reqId);
        Assert.assertNull(res);

        List<VerdictResultModel> verdictResultModelList = Lists.newArrayList();
        VerdictResultModel m1 = new VerdictResultModel();
        m1.setReqId(reqId);
        m1.setPtype("16");
        m1.setCaseResult("已结案");
        m1.setSentenceEffect("99");
        verdictResultModelList.add(m1);
        res = tendencyJudgResult.execQuota(reqId);
        Assert.assertNull("else", res);

        VerdictResultModel m2 = new VerdictResultModel();
        m2.setReqId(reqId);
        m2.setPtype("16");
        m2.setCaseResult("已结案");
        m2.setSentenceEffect("0");
        res = tendencyJudgResult.execQuota(reqId);
        Assert.assertNull("else", res);

        VerdictResultModel m3 = new VerdictResultModel();
        m3.setReqId(reqId);
        m3.setPtype("16");
        m3.setCaseResult("已结案");
        m3.setSentenceEffect("1");
        res = tendencyJudgResult.execQuota(reqId);
        Assert.assertNull("else", res);

        VerdictResultModel m4 = new VerdictResultModel();
        m4.setReqId(reqId);
        m4.setPtype("16");
        m4.setCaseResult("已结案");
        m4.setSentenceEffect("1");
        res = tendencyJudgResult.execQuota(reqId);
        Assert.assertNull("败诉", res);


    }

}
