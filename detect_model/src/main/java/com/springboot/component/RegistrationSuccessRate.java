package com.springboot.component;

import com.springboot.constant.GlobalConstants;
import com.springboot.domain.StdEntBasic;
import com.springboot.mapper.ExeSqlMapper;
import com.springboot.service.StdEntBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;

@Component("RegistrationSuccessRate")
public class RegistrationSuccessRate implements QuotaComponent{
    @Autowired
    private ExeSqlMapper exeSqlMapper;
    @Autowired
    private StdEntBasicService stdEntBasicService;

    @Override
    public String execQuota(String reqId) {

        String entName = "";
        StdEntBasic stdEntBasic = stdEntBasicService.getStdEntBasicByReqId(reqId);
        if(stdEntBasic != null && stdEntBasic.getEntName() != null) {
            entName = stdEntBasic.getEntName();
        }

        String molecule = "select count(distinct registrationnumber) as keyvalue \n" +
                "from std_ia_brand \n" +
                "where authoritystatus = '已注册' \n" +
                "and applicationname like '%"+entName+"%' \n" +
                "and req_id = '"+reqId+"'";

        HashMap map1 = exeSqlMapper.exeQuotaSql(molecule);

        String denominator = "select count(distinct registrationnumber) as keyvalue \n" +
                "from std_ia_brand \n" +
                "where authoritystatus in ('待审中', '已注册', '已初审') \n" +
                "and applicationname like '%"+entName+"%' \n" +
                "and req_id = '"+reqId+"'";

        HashMap map2 = exeSqlMapper.exeQuotaSql(denominator);


        BigDecimal moleculeNum = new BigDecimal(map1.get("keyvalue").toString());
        BigDecimal denominatorNum = new BigDecimal(map2.get("keyvalue").toString());
        if(denominatorNum.equals(BigDecimal.ZERO)) {
            if(moleculeNum.compareTo(BigDecimal.ZERO) > 0){
                return GlobalConstants.INF;
            }else {
                return null;
            }
        }

        return moleculeNum.divide(denominatorNum,2, BigDecimal.ROUND_HALF_UP).toString();
    }
}
