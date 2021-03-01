package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.Company;
import com.springboot.dto.CompanyImportDto;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.CompanyMapper;
import com.springboot.service.CompanyService;
import com.springboot.service.remote.GeoRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lhf
 * @date 2021/3/1 3:46 下午
 **/
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private GeoRemoteService geoRemoteService;

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
            companies.forEach(company -> {
                geo(company);
            });

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
