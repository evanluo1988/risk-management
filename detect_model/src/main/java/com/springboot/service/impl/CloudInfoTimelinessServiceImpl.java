package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.CloudInfoTimeliness;
import com.springboot.enums.EnableEnum;
import com.springboot.mapper.CloudInfoTimelinessMapper;
import com.springboot.mapper.ExeSqlMapper;
import com.springboot.service.CloudInfoTimelinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CloudInfoTimelinessServiceImpl extends ServiceImpl<CloudInfoTimelinessMapper, CloudInfoTimeliness> implements CloudInfoTimelinessService {
    @Autowired
    private CloudInfoTimelinessMapper cloudInfoTimelinessMapper;
    @Autowired
    private ExeSqlMapper exeSqlMapper;

    @Override
    public boolean checkTimeliness(String entName) {
        //没找到或时间过期，则时效性过期
        CloudInfoTimeliness cloudInfoTimeliness = getCloudInfoTimelinessByEntName(entName);
        return checkTimeliness(cloudInfoTimeliness);
    }

    @Override
    public boolean checkTimeliness(CloudInfoTimeliness cloudInfoTimeliness) {
        if(cloudInfoTimeliness == null){
            return Boolean.FALSE;
        }
        if(LocalDateTime.now().isAfter(cloudInfoTimeliness.getExpireTime())){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public CloudInfoTimeliness getCloudInfoTimelinessByEntName(String entName) {
        LambdaQueryWrapper<CloudInfoTimeliness> queryWrapper = new LambdaQueryWrapper<CloudInfoTimeliness>()
                .eq(CloudInfoTimeliness::getEntName, entName)
                .eq(CloudInfoTimeliness::getStatus, 'Y');
        return getOne(queryWrapper,false);
    }

    /**
     * 更新时效性
     * @param entName
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTimeLiness(String entName, String reqId) {
        CloudInfoTimeliness cloudInfoTimeliness = getCloudInfoTimelinessByEntName(entName);
        if(cloudInfoTimeliness != null) {
            cloudInfoTimeliness.setStatus("N");
            cloudInfoTimelinessMapper.updateById(cloudInfoTimeliness);
        }

        cloudInfoTimeliness = new CloudInfoTimeliness();
        cloudInfoTimeliness.setReqId(reqId);
        cloudInfoTimeliness.setEntName(entName);
        cloudInfoTimeliness.setStatus("Y");
        cloudInfoTimeliness.setExpireTime(LocalDateTime.now().plusDays(30));
        cloudInfoTimelinessMapper.insert(cloudInfoTimeliness);
    }

    @Override
    public List<CloudInfoTimeliness> listExpired(LocalDateTime expiredTime) {
        LambdaQueryWrapper<CloudInfoTimeliness> queryWrapper = new LambdaQueryWrapper<CloudInfoTimeliness>()
                .le(CloudInfoTimeliness::getExpireTime, expiredTime)
                .eq(CloudInfoTimeliness::getDeleted, EnableEnum.N.getCode());
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clean(String reqId) {
        List<String> tableNames = Arrays.asList("ent_wy_alterlist","ent_wy_basiclist","ent_wy_caseinfolist","ent_wy_entinvitemlist","ent_wy_exceptionlist","ent_wy_filiationlist","ent_wy_frinvlist","ent_wy_frpositionlist","ent_wy_liquidationlist","ent_wy_mortgagealtlist","ent_wy_mortgagebasiclist","ent_wy_mortgagecanlist","ent_wy_mortgagedebtlist","ent_wy_mortgagepawnlist","ent_wy_mortgageperlist","ent_wy_mortgagereglist","ent_wy_personlist","ent_wy_ryposfrlist","ent_wy_ryposperlist","ent_wy_ryposshalist","ent_wy_shareholderlist","ent_wy_sharesfrostlist","ent_wy_stockpawnaltlist","ent_wy_stockpawncanlist","ent_wy_stockpawnlist",
                "std_ent_alter_list","std_ent_basic_list","std_ent_caseinfo_list","std_ent_exceptions","std_ent_filiation_list","std_ent_invitem_list","std_ent_liquidation_list","std_ent_lrinv_list","std_ent_lrposition_list","std_ent_mortgagealts","std_ent_mortgagebasics","std_ent_mortgagecans","std_ent_mortgagedebts","std_ent_mortgagepawns","std_ent_mortgagepers","std_ent_mortgageregs","std_ent_person_list","std_ent_ryposlr_list","std_ent_ryposper_list","std_ent_rypossha_list","std_ent_share_holder_list","std_ent_sharesfrost_list","std_ent_stockpawnalts","std_ent_stockpawnrevs","std_ent_stockpawns","std_ia_brand","std_ia_copyright","std_ia_partent","std_ia_partent_catalog","std_ia_partent_detail","std_legal_casemedian","std_legal_casemedian_temp","std_legal_data_added","std_legal_data_structured","std_legal_data_structured_temp","std_legal_ent_unexecuted","std_legal_ent_unexecuted_temp","std_legal_enterprise_executed","std_legal_enterprise_executed_temp","std_legal_ind_unexecuted","std_legal_individual_executed",
                "legal_wy_bzxr_com","legal_wy_bzxr_man","legal_wy_ssjghsj","legal_wy_sxbzxr_com","legal_wy_sxbzxr_man",
                "ia_as_brand","ia_as_copyright","ia_as_partent","ia_as_partent_catalog","ia_as_partent_detail");

        for (String tableName : tableNames) {
            exeSqlMapper.deleteByTableNameAndReqId(tableName,reqId);
        }

        updateCloudInfoTimelinessDeleted(reqId);
    }

    private void updateCloudInfoTimelinessDeleted(String reqId) {
        LambdaUpdateWrapper<CloudInfoTimeliness> updateWrapper = new LambdaUpdateWrapper<CloudInfoTimeliness>()
                .set(CloudInfoTimeliness::getDeleted, EnableEnum.Y.getCode())
                .eq(CloudInfoTimeliness::getReqId, reqId);
        this.update(updateWrapper);
    }

}
