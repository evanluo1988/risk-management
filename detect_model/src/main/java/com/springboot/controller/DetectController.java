package com.springboot.controller;


import com.springboot.enums.OrgEnum;
import com.springboot.service.RiskDetectionService;
import com.springboot.vo.risk.EntHealthReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public EntHealthReportVo detectEntForFinance(@PathVariable String entName) {
        return riskDetectionService.checkByEntName(entName, OrgEnum.FINANCE_OFFICE);
    }

    /**
     * 科技局
     * @param entName
     * @return
     */
    @GetMapping("/science/{entName}")
    public EntHealthReportVo detectEntForScience(@PathVariable String entName) {
        return riskDetectionService.checkByEntName(entName, OrgEnum.SCIENCE_OFFICE);
    }

}
