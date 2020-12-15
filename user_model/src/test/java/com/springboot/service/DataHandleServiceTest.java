package com.springboot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.ApplicationTest;
import com.springboot.domain.risk.*;
import com.springboot.mapper.*;
import com.springboot.model.RemoteDataModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
public class DataHandleServiceTest extends ApplicationTest {
    @Autowired
    private DataHandleService dataHandleService;
    @Autowired
    private EntWyBasicMapper entWyBasicMapper;
    @Autowired
    private EntWyShareholderMapper entWyShareholderMapper;
    @Autowired
    private EntWyPersonMapper entWyPersonMapper;
    @Autowired
    private EntWyEntinvitemMapper entWyEntinvitemMapper;
    @Autowired
    private EntWyFrinvMapper entWyFrinvMapper;
    @Autowired
    private EntWyFrpositionMapper entWyFrpositionMapper;
    @Autowired
    private EntWyFiliationMapper entWyFiliationMapper;
    @Autowired
    private EntWyLiquidationMapper entWyLiquidationMapper;
    @Autowired
    private EntWyAlterMapper entWyAlterMapper;
    @Autowired
    private EntWyMortgagebasicMapper entWyMortgagebasicMapper;

    @Test
    public void testHandelData(){
        dataHandleService.handelData("广西南宁卓信商贸有限公司");
    }

    @Test
    public void testHandleDataCorrect() throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/msg.txt");
        String msg = IOUtils.toString(resourceAsStream);
        String reqId = "71183acb-7543-499e-aad7-cb862a2ab3ed";
        JSONObject jsonObject = JSONObject.parseObject(msg);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray gsDataJsonArray = (JSONArray)dataObject.getJSONObject("R11C53").get("data");

        RemoteDataModel data = JSON.parseObject(gsDataJsonArray.get(0).toString(), RemoteDataModel.class);
        //basicList
        List<EntWyBasic> basicList = data.getBasicList();
        List<EntWyBasic> entWyBasics = entWyBasicMapper.selectList(new LambdaQueryWrapper<EntWyBasic>().eq(EntWyBasic::getReqId, reqId));

        if (!basicList.equals(entWyBasics)){
            log.error("EntWyBasic 数据不匹配 basicList:{}，entWyBasics:{}",basicList,entWyBasics);
        }else{
            log.info("EntWyBasic 数据匹配 basicList:{}，entWyBasics:{}",basicList,entWyBasics);
        }

        //shareholderList
        List<EntWyShareholder> shareholderList = data.getShareholderList();
        List<EntWyShareholder> entWyShareholders = entWyShareholderMapper.selectList(new LambdaQueryWrapper<EntWyShareholder>().eq(EntWyShareholder::getReqId, reqId));

        if (!shareholderList.equals(entWyShareholders)){
            log.error("EntWyShareholder 数据不匹配 shareholderList:{}，entWyShareholders:{}",shareholderList,entWyShareholders);
        }else{
            log.info("EntWyShareholder 数据匹配 shareholderList:{}，entWyShareholders:{}",shareholderList,entWyShareholders);
        }

        //personList
        List<EntWyPerson> personList = data.getPersonList();
        List<EntWyPerson> entWyPeople = entWyPersonMapper.selectList(new LambdaQueryWrapper<EntWyPerson>().eq(EntWyPerson::getReqId, reqId));
        if (!personList.equals(entWyPeople)){
            log.error("EntWyPerson 数据不匹配 personList:{}，entWyPeople:{}",personList,entWyPeople);
        }else{
            log.info("EntWyPerson 数据匹配 personList:{}，entWyPeople:{}",personList,entWyPeople);
        }

        //entInvItemList
        List<EntWyEntinvitem> entInvItemList = data.getEntInvItemList();
        List<EntWyEntinvitem> entWyEntinvitems = entWyEntinvitemMapper.selectList(new LambdaQueryWrapper<EntWyEntinvitem>().eq(EntWyEntinvitem::getReqId, reqId));
        if (!entInvItemList.equals(entWyEntinvitems)){
            log.error("EntWyEntinvitem 数据不匹配 entInvItemList:{}，entWyEntinvitems:{}",entInvItemList,entWyEntinvitems);
        }else{
            log.info("EntWyEntinvitem 数据匹配 entInvItemList:{}，entWyEntinvitems:{}",entInvItemList,entWyEntinvitems);
        }

        //frInvList
        List<EntWyFrinv> frInvList = data.getFrInvList();
        List<EntWyFrinv> entWyFrinvs = entWyFrinvMapper.selectList(new LambdaQueryWrapper<EntWyFrinv>().eq(EntWyFrinv::getReqId, reqId));
        if (!frInvList.equals(entWyFrinvs)){
            log.error("EntWyFrinv 数据不匹配 frInvList:{}，entWyFrinvs:{}",frInvList,entWyFrinvs);
        }else{
            log.info("EntWyFrinv 数据匹配 frInvList:{}，entWyFrinvs:{}",frInvList,entWyFrinvs);
        }

        //frPositionList
        List<EntWyFrposition> frPositionList = data.getFrPositionList();
        List<EntWyFrposition> entWyFrpositions = entWyFrpositionMapper.selectList(new LambdaQueryWrapper<EntWyFrposition>().eq(EntWyFrposition::getReqId, reqId));
        if (!frPositionList.equals(entWyFrpositions)){
            log.error("EntWyFrposition 数据不匹配 frPositionList:{}，entWyFrpositions:{}",frPositionList,entWyFrpositions);
        }else{
            log.info("EntWyFrposition 数据匹配 frPositionList:{}，entWyFrpositions:{}",frPositionList,entWyFrpositions);
        }

        //filiationList
        List<EntWyFiliation> filiationList = data.getFiliationList();
        List<EntWyFiliation> entWyFiliations = entWyFiliationMapper.selectList(new LambdaQueryWrapper<EntWyFiliation>().eq(EntWyFiliation::getReqId, reqId));
        if (!filiationList.equals(entWyFiliations)){
            log.error("EntWyFiliation 数据不匹配 filiationList:{}，entWyFiliations:{}",filiationList,entWyFiliations);
        }else{
            log.info("EntWyFiliation 数据匹配 filiationList:{}，entWyFiliations:{}",filiationList,entWyFiliations);
        }

        //liquidationList
        List<EntWyLiquidation> liquidationList = data.getLiquidationList();
        List<EntWyLiquidation> entWyLiquidations = entWyLiquidationMapper.selectList(new LambdaQueryWrapper<EntWyLiquidation>().eq(EntWyLiquidation::getReqId, reqId));
        if (!liquidationList.equals(entWyLiquidations)){
            log.error("EntWyLiquidation 数据不匹配 liquidationList:{}，entWyFiliations:{}",liquidationList,entWyLiquidations);
        }else{
            log.info("EntWyLiquidation 数据匹配 liquidationList:{}，entWyFiliations:{}",liquidationList,entWyLiquidations);
        }

        //alterList
        List<EntWyAlter> alterList = data.getAlterList();
        List<EntWyAlter> entWyAlters = entWyAlterMapper.selectList(new LambdaQueryWrapper<EntWyAlter>().eq(EntWyAlter::getReqId, reqId));
        if (!alterList.equals(entWyAlters)){
            log.error("EntWyAlter 数据不匹配 alterList:{}，entWyAlters:{}",alterList,entWyAlters);
        }else{
            log.info("EntWyAlter 数据匹配 alterList:{}，entWyAlters:{}",alterList,entWyAlters);
        }

        //mortgageBasicList
        List<EntWyMortgagebasic> mortgageBasicList = data.getMortgageBasicList();
        List<EntWyMortgagebasic> entWyMortgagebasics = entWyMortgagebasicMapper.selectList(new LambdaQueryWrapper<EntWyMortgagebasic>().eq(EntWyMortgagebasic::getReqId, reqId));
        if (!mortgageBasicList.equals(entWyMortgagebasics)){
            log.error("EntWyMortgagebasic 数据不匹配 mortgageBasicList:{}，entWyMortgagebasics:{}",mortgageBasicList,entWyMortgagebasics);
        }else{
            log.info("EntWyMortgagebasic 数据匹配 mortgageBasicList:{}，entWyMortgagebasics:{}",mortgageBasicList,entWyMortgagebasics);
        }

        //mortgageRegList

        //mortgagePawnList

        //mortgageAltList

        //mortgageCanList

        //mortgageDebtList

        //mortgagePerList

        //stockPawnList

        //stockPawnAltList

        //stockPawnCanList

        //caseInfoList

        //exceptionList

    }

  @Test
    public void testCulQuotas() {
        String reqId = "345aa469-1574-45c2-84cc-9e80b1346fd6";
        dataHandleService.culQuotas(reqId);
    }

}
