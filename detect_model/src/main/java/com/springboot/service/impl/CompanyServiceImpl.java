package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.Company;
import com.springboot.domain.CompanyDetectErrLog;
import com.springboot.dto.CompanyImportDto;
import com.springboot.enums.OrgEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.CompanyMapper;
import com.springboot.service.CompanyDetectErrLogService;
import com.springboot.service.CompanyService;
import com.springboot.service.DataHandleService;
import com.springboot.service.remote.GeoRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author lhf
 * @date 2021/3/1 3:46 下午
 **/
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private GeoRemoteService geoRemoteService;
    @Autowired
    private DataHandleService dataHandleService;
    @Autowired
    private CompanyDetectErrLogService companyDetectErrLogService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importCompany(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), CompanyImportDto.class, new CompanyUploadDataListener(this,geoRemoteService)).sheet().headRowNumber(1).doRead();
        } catch (IOException e) {
            log.error("导入举报信息异常：{}", e);
            throw new ServiceException("导入举报信息错误");
        }
    }

    @Override
    public void detect() {
        String timePattern = "yyyyMMddHHmmssSSS";
        List<Company> list = list();
        String random = UUID.randomUUID().toString();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern(timePattern));
        String index = now+random;
        log.info("detect companies data start,index:{}",index);
        List<CompanyDetectErrLog> errorLogs = Lists.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            Company company = list.get(i);
            log.info("detect companies process {} / {}",i,list.size());
            try{
                // 查询数据
                String reqId = dataHandleService.handelData(company.getEntName(), OrgEnum.SCIENCE_OFFICE);
                // 计算指标值
                dataHandleService.culQuotas(reqId, OrgEnum.SCIENCE_OFFICE);
                company.setReqId(reqId);
                updateById(company);
            }catch (Exception e){
                log.error("公司工商司法知识产权数据跑批异常index:{},e:{}",index,JSON.toJSONString(e));
                CompanyDetectErrLog companyDetectErrLog = new CompanyDetectErrLog();
                companyDetectErrLog.setCompanyId(company.getId())
                        .setCompanyName(company.getEntName())
                        .setExceptionMsg(JSON.toJSONString(e))
                        .setQueryIndex(index);
                errorLogs.add(companyDetectErrLog);
            }
        }
        // 记录错误日志
        if (!CollectionUtils.isEmpty(errorLogs)){
            companyDetectErrLogService.saveBatch(errorLogs);
        }
        log.info("detect companies data end,index:{}",index);
    }


    @Slf4j
    private static class CompanyUploadDataListener extends AnalysisEventListener<CompanyImportDto> {

        String geoKey = "1d0a08b7909df6a1a2d272c5a1aa40d0";
        private List<CompanyImportDto> data;
        private CompanyService companyService;
        private GeoRemoteService geoRemoteService;

        public CompanyUploadDataListener(CompanyService companyService,GeoRemoteService geoRemoteService) {
            data = Lists.newArrayList();
            this.companyService = companyService;
            this.geoRemoteService = geoRemoteService;
        }

        @Override
        public void invoke(CompanyImportDto companyImportDto, AnalysisContext analysisContext) {
            data.add(companyImportDto);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            List<Company> companies = data.stream().map(CompanyImportDto::toCompany).collect(Collectors.toList());
            companies.forEach(CompanyUploadDataListener.this::geo);

            companyService.saveBatch(companies);
        }

        private void geo(Company company) {
            try {
                log.info("开始获取当前企业地图信息：{}", JSON.toJSONString(company));
                GeoRemoteService.GeoResponse geo = geoRemoteService.geo(geoKey, company.getRegAddress());
                if (geo.succ()) {
                    String location = geo.getGeocodes().get(0).getLocation();
                    String[] split = location.split(",");
                    if (split.length == 2) {
                        String lon = split[0];
                        String lat = split[1];
                        company.setLon(lon);
                        company.setLat(lat);
                    }

                    GeoRemoteService.ReGeoResponse regeo = geoRemoteService.regeo(geoKey, location, "base", "false", "1");
                    if (regeo.succ()) {
                        GeoRemoteService.ReGeoResponse.ReGeoCode.AddressComponent addressComponent = regeo.getRegeocode().getAddressComponent();
                        company.setZone(addressComponent.getDistrict());
                        company.setStreet(addressComponent.getTownship());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
