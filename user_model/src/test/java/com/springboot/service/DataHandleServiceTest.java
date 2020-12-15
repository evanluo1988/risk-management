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
    @Autowired
    private EntWyMortgageregMapper entWyMortgageregMapper;
    @Autowired
    private EntWyMortgagepawnMapper entWyMortgagepawnMapper;
    @Autowired
    private EntWyMortgagealtMapper entWyMortgagealtMapper;
    @Autowired
    private EntWyMortgagecanMapper entWyMortgagecanMapper;
    @Autowired
    private EntWyMortgagedebtMapper entWyMortgagedebtMapper;
    @Autowired
    private EntWyMortgageperMapper entWyMortgageperMapper;
    @Autowired
    private EntWyStockpawnMapper entWyStockpawnMapper;
    @Autowired
    private EntWyStockpawnaltMapper entWyStockpawnaltMapper;
    @Autowired
    private EntWyStockpawncanMapper entWyStockpawncanMapper;
    @Autowired
    private EntWyCaseinfoMapper entWyCaseinfoMapper;
    @Autowired
    private EntWyExceptionMapper entWyExceptionMapper;

    @Test
    public void testHandelData(){
        dataHandleService.handelData("广西南宁卓信商贸有限公司");
    }

    @Test
    public void testHandleDataCorrect() throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/msg.txt");
        String msg = IOUtils.toString(resourceAsStream);
        String reqId = "8ef1351a-62b8-4213-810e-3ac0461cd68d";
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
        List<EntWyMortgagereg> mortgageRegList = data.getMortgageRegList();
        List<EntWyMortgagereg> entWyMortgageregs = entWyMortgageregMapper.selectList(new LambdaQueryWrapper<EntWyMortgagereg>().eq(EntWyMortgagereg::getReqId, reqId));
        if (!mortgageRegList.equals(entWyMortgageregs)){
            log.error("EntWyMortgagereg 数据不匹配 mortgageRegList:{}，entWyMortgageregs:{}",mortgageRegList,entWyMortgageregs);
        }else{
            log.info("EntWyMortgagereg 数据匹配 mortgageRegList:{}，entWyMortgageregs:{}",mortgageRegList,entWyMortgageregs);
        }

        //mortgagePawnList
        List<EntWyMortgagepawn> mortgagePawnList = data.getMortgagePawnList();
        List<EntWyMortgagepawn> entWyMortgagepawns = entWyMortgagepawnMapper.selectList(new LambdaQueryWrapper<EntWyMortgagepawn>().eq(EntWyMortgagepawn::getReqId, reqId));
        if (!mortgagePawnList.equals(entWyMortgagepawns)){
            log.error("EntWyMortgagepawn 数据不匹配 mortgagePawnList:{}，entWyMortgagepawns:{}",mortgagePawnList,entWyMortgagepawns);
        }else{
            log.info("EntWyMortgagepawn 数据匹配 mortgagePawnList:{}，entWyMortgagepawns:{}",mortgagePawnList,entWyMortgagepawns);
        }

        //mortgageAltList
        List<EntWyMortgagealt> mortgageAltList = data.getMortgageAltList();
        List<EntWyMortgagealt> entWyMortgagealts = entWyMortgagealtMapper.selectList(new LambdaQueryWrapper<EntWyMortgagealt>().eq(EntWyMortgagealt::getReqId, reqId));
        if (!mortgageAltList.equals(entWyMortgagealts)){
            log.error("EntWyMortgagealt 数据不匹配 mortgageAltList:{}，entWyMortgagealts:{}",mortgageAltList,entWyMortgagealts);
        }else{
            log.info("EntWyMortgagealt 数据匹配 mortgageAltList:{}，entWyMortgagealts:{}",mortgageAltList,entWyMortgagealts);
        }

        //mortgageCanList
        List<EntWyMortgagecan> mortgageCanList = data.getMortgageCanList();
        List<EntWyMortgagecan> entWyMortgagecans = entWyMortgagecanMapper.selectList(new LambdaQueryWrapper<EntWyMortgagecan>().eq(EntWyMortgagecan::getReqId, reqId));
        if (!mortgageCanList.equals(entWyMortgagecans)){
            log.error("EntWyMortgagecan 数据不匹配 mortgageCanList:{}，entWyMortgagecans:{}",mortgageCanList,entWyMortgagecans);
        }else{
            log.info("EntWyMortgagecan 数据匹配 mortgageCanList:{}，entWyMortgagecans:{}",mortgageCanList,entWyMortgagecans);
        }

        //mortgageDebtList
        List<EntWyMortgagedebt> mortgageDebtList = data.getMortgageDebtList();
        List<EntWyMortgagedebt> entWyMortgagedebts = entWyMortgagedebtMapper.selectList(new LambdaQueryWrapper<EntWyMortgagedebt>().eq(EntWyMortgagedebt::getReqId, reqId));
        if (!mortgageDebtList.equals(entWyMortgagedebts)){
            log.error("EntWyMortgagedebt 数据不匹配 mortgageDebtList:{}，entWyMortgagedebts:{}",mortgageDebtList,entWyMortgagedebts);
        }else{
            log.info("EntWyMortgagedebt 数据匹配 mortgageDebtList:{}，entWyMortgagedebts:{}",mortgageDebtList,entWyMortgagedebts);
        }

        //mortgagePerList
        List<EntWyMortgageper> mortgagePerList = data.getMortgagePerList();
        List<EntWyMortgageper> entWyMortgagepers = entWyMortgageperMapper.selectList(new LambdaQueryWrapper<EntWyMortgageper>().eq(EntWyMortgageper::getReqId, reqId));
        if (!mortgagePerList.equals(entWyMortgagepers)){
            log.error("EntWyMortgageper 数据不匹配 mortgagePerList:{}，entWyMortgagepers:{}",mortgagePerList,entWyMortgagepers);
        }else{
            log.info("EntWyMortgageper 数据匹配 mortgagePerList:{}，entWyMortgagepers:{}",mortgagePerList,entWyMortgagepers);
        }

        //stockPawnList
        List<EntWyStockpawn> stockPawnList = data.getStockPawnList();
        List<EntWyStockpawn> entWyStockpawns = entWyStockpawnMapper.selectList(new LambdaQueryWrapper<EntWyStockpawn>().eq(EntWyStockpawn::getReqId, reqId));
        if (!stockPawnList.equals(entWyStockpawns)){
            log.error("EntWyStockpawn 数据不匹配 stockPawnList:{}，entWyStockpawns:{}",stockPawnList,entWyStockpawns);
        }else{
            log.info("EntWyStockpawn 数据匹配 stockPawnList:{}，entWyStockpawns:{}",stockPawnList,entWyStockpawns);
        }

        //stockPawnAltList
        List<EntWyStockpawnalt> stockPawnAltList = data.getStockPawnAltList();
        List<EntWyStockpawnalt> entWyStockpawnalts = entWyStockpawnaltMapper.selectList(new LambdaQueryWrapper<EntWyStockpawnalt>().eq(EntWyStockpawnalt::getReqId, reqId));
        if (!stockPawnAltList.equals(entWyStockpawnalts)){
            log.error("EntWyStockpawnalt 数据不匹配 stockPawnAltList:{}，entWyStockpawnalts:{}",stockPawnAltList,entWyStockpawnalts);
        }else{
            log.info("EntWyStockpawnalt 数据匹配 stockPawnAltList:{}，entWyStockpawnalts:{}",stockPawnAltList,entWyStockpawnalts);
        }

        //stockPawnCanList
        List<EntWyStockpawncan> stockPawnCanList = data.getStockPawnCanList();
        List<EntWyStockpawncan> entWyStockpawncans = entWyStockpawncanMapper.selectList(new LambdaQueryWrapper<EntWyStockpawncan>().eq(EntWyStockpawncan::getReqId, reqId));
        if (!stockPawnCanList.equals(entWyStockpawncans)){
            log.error("EntWyStockpawncan 数据不匹配 stockPawnCanList:{}，entWyStockpawncans:{}",stockPawnCanList,entWyStockpawncans);
        }else{
            log.info("EntWyStockpawncan 数据匹配 stockPawnCanList:{}，entWyStockpawncans:{}",stockPawnCanList,entWyStockpawncans);
        }

        //caseInfoList
        List<EntWyCaseinfo> caseInfoList = data.getCaseInfoList();
        List<EntWyCaseinfo> entWyCaseinfos = entWyCaseinfoMapper.selectList(new LambdaQueryWrapper<EntWyCaseinfo>().eq(EntWyCaseinfo::getReqId, reqId));
        if (!caseInfoList.equals(entWyCaseinfos)){
            log.error("EntWyCaseinfo 数据不匹配 caseInfoList:{}，entWyCaseinfos:{}",caseInfoList,entWyCaseinfos);
        }else{
            log.info("EntWyCaseinfo 数据匹配 caseInfoList:{}，entWyCaseinfos:{}",caseInfoList,entWyCaseinfos);
        }

        //exceptionList
        List<EntWyException> exceptionList = data.getExceptionList();
        List<EntWyException> entWyExceptions = entWyExceptionMapper.selectList(new LambdaQueryWrapper<EntWyException>().eq(EntWyException::getReqId, reqId));
        if (!exceptionList.equals(entWyExceptions)){
            log.error("EntWyException 数据不匹配 exceptionList:{}，entWyExceptions:{}",exceptionList,entWyExceptions);
        }else{
            log.info("EntWyException 数据匹配 exceptionList:{}，entWyExceptions:{}",exceptionList,entWyExceptions);
        }

    }

  @Test
    public void testCulQuotas() {
        String reqId = "345aa469-1574-45c2-84cc-9e80b1346fd6";
        dataHandleService.culQuotas(reqId);
    }

}
