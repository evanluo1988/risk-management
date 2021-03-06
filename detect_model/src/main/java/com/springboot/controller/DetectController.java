package com.springboot.controller;


import com.springboot.enums.OrgEnum;
import com.springboot.ret.ReturnT;
import com.springboot.service.RiskDetectionService;
import com.springboot.utils.FreemarkerUtils;
import com.springboot.utils.ITextUtils;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.risk.EntHealthReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/detect")
public class DetectController {
    @Autowired
    private RiskDetectionService riskDetectionService;

    /**
     * 金融办
     * @param entName
     * @return
     */
    @GetMapping("/finance/{entName}")
    public ReturnT<EntHealthReportVo> detectEntForFinance(@PathVariable String entName) {
        return ReturnTUtils.getReturnT(riskDetectionService.checkByEntName(entName, OrgEnum.FINANCE_OFFICE));
    }

    @GetMapping("/finance/pdf/{entName}")
    public void pdfDetectEntForFinance(@PathVariable String entName, HttpServletResponse response) throws IOException {
        EntHealthReportVo entHealthReportVo = riskDetectionService.checkByEntName(entName, OrgEnum.FINANCE_OFFICE);
        HashMap data = new HashMap();
        data.put("data",entHealthReportVo);

        String html = FreemarkerUtils.loadFtlHtml("/templates", "pdf-finance.ftl",data);
        ITextUtils.convertHtmlToPdf(response.getOutputStream(),html);
    }

    /**
     * 科技局
     * @param entName
     * @return
     */
    @GetMapping("/science/{entName}")
    public ReturnT<EntHealthReportVo> detectEntForScience(@PathVariable String entName) {
        return ReturnTUtils.getReturnT(riskDetectionService.checkByEntName(entName, OrgEnum.SCIENCE_OFFICE));
    }

    @GetMapping("/science/pdf/{entName}")
    public void pdfDetectEntForScience(@PathVariable String entName, HttpServletResponse response) throws IOException {
        EntHealthReportVo entHealthReportVo = riskDetectionService.checkByEntName(entName, OrgEnum.SCIENCE_OFFICE);
        HashMap data = new HashMap();
        data.put("data",entHealthReportVo);

        String html = FreemarkerUtils.loadFtlHtml("/templates/", "pdf-science.ftl",data);
        ITextUtils.convertHtmlToPdf(response.getOutputStream(),html);
    }
}
