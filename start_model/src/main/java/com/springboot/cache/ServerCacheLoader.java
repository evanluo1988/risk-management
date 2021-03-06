package com.springboot.cache;

import com.springboot.service.*;
import com.springboot.utils.DetectCacheUtils;
import com.springboot.utils.ServerCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerCacheLoader implements CommandLineRunner {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private EtlTranRuleService etlTranRuleService;
    @Autowired
    private TableStructService tableStructService;
    @Autowired
    private QuotaRuleService quotaRuleService;
    @Autowired
    private QuotaGrandService quotaGrandService;
    @Autowired
    private QuotaDimensionService quotaDimensionService;
    @Autowired
    private LegalDataAddColumnService legalDataAddColumnService;
    @Autowired
    private DicTableService dicTableService;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void run(String... args) {
        log.info("cache context path ......");
        ServerCacheUtils.setContextPathCache(contextPath);
        log.info("loading menu list......");
        ServerCacheUtils.setMenuListCache(menuService.findAllMenus());
        log.info("loading role permission......");
        ServerCacheUtils.setRolePermissionCache(roleService.findAllRolePermission());
        log.info("loading area ......");
        ServerCacheUtils.setAreas(areaService.list());
        log.info("loading etl tran rule list......");
        DetectCacheUtils.setEtlTranRuleListCache(etlTranRuleService.findEnableRules());
        log.info("loading std table struct......");
        DetectCacheUtils.setStdTableMap(tableStructService.getStdTableStruct());
        log.info("loading quota list......");
        DetectCacheUtils.setQuotaList(quotaRuleService.findEnableQuotaRules());
        log.info("loading quota grand list......");
        DetectCacheUtils.setQuotaGrandList(quotaGrandService.findQuotaGrandList());
        log.info("loading quota dimensions......");
        DetectCacheUtils.setQuotaDimensionList(quotaDimensionService.getAllQuotaDimensions());
        log.info("loading analysis of justice data......");
        legalDataAddColumnService.initAnalysisJudicialEngine();
        log.info("loading dic table data......");
        DetectCacheUtils.setDicTable(dicTableService.getDicTableList());

    }
}
