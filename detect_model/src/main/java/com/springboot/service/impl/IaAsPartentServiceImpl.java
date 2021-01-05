package com.springboot.service.impl;

import com.springboot.domain.IaAsPartent;
import com.springboot.domain.IaAsPartentCatalog;
import com.springboot.domain.IaAsPartentDetail;
import com.springboot.mapper.IaAsPartentCatalogMapper;
import com.springboot.mapper.IaAsPartentDetailMapper;
import com.springboot.mapper.IaAsPartentMapper;
import com.springboot.model.IaAsPartentDetailModel;
import com.springboot.model.IaAsPartentModel;
import com.springboot.service.IaAsPartentService;
import com.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IaAsPartentServiceImpl implements IaAsPartentService {
    @Autowired
    private IaAsPartentMapper iaAsPartentMapper;
    @Autowired
    private IaAsPartentDetailMapper iaAsPartentDetailMapper;
    @Autowired
    private IaAsPartentCatalogMapper iaAsPartentCatalogMapper;

    @Override
    public void savePartent(IaAsPartentModel iaAsPartentModel) {
        IaAsPartent iaAsPartent = iaAsPartentModel;
        iaAsPartentMapper.insert(iaAsPartent);

        for(IaAsPartentDetailModel iaAsPartentDetailModel : Utils.getList(iaAsPartentModel.getDetailList())) {
            IaAsPartentDetail iaAsPartentDetail = iaAsPartentDetailModel;
            iaAsPartentDetail.setIaAsPartentId(iaAsPartent.getId());
            iaAsPartentDetail.setReqId(iaAsPartent.getReqId());
            iaAsPartentDetailMapper.insert(iaAsPartentDetail);

            IaAsPartentCatalog iaAsPartentCatalog = iaAsPartentDetailModel.getCatalogPatent();
            iaAsPartentCatalog.setReqId(iaAsPartent.getReqId());
            iaAsPartentCatalog.setIaAsPartentDetailId(iaAsPartentDetail.getId());
            iaAsPartentCatalogMapper.insert(iaAsPartentCatalog);
        }
    }
}
