package com.springboot.utils;

import com.springboot.domain.Area;
import com.springboot.domain.Menu;
import com.springboot.domain.risk.EtlTranRule;
import com.springboot.domain.risk.Quota;
import com.springboot.domain.risk.QuotaGrand;
import com.springboot.model.RolePerm;
import com.springboot.model.StdTable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerCacheUtils {
    private static List<Menu> menuListCache;
    /**
     * rolePerm缓存，key：roleId，value：RolePerm
     */
    private static Map<Long, RolePerm> rolePermCache;
    /**
     * area缓存
     */
    private static Collection<Area> areaCache;
    /**
     * context path
     */
    private static String contextPath;

    /**
     * 转换规则（原始表到标准表规则）
     */
    private static List<EtlTranRule> etlTranRuleList;

    /**
     * 表类型转换
     * <tableName, StdTable>
     */
    private static Map<String, StdTable> stdTableMap;

    /**
     * 指标规则表
     */
    private static List<Quota> quotaList;

    /**
     * 指标分档表
     */
    private static Map<String, List<QuotaGrand>> quotaGrandMap;

    public static void setQuotaGrandList(List<QuotaGrand> quotaGrandList){
        ServerCacheUtils.quotaGrandMap = quotaGrandList.stream().collect(Collectors.groupingBy(QuotaGrand::getGrandCode));
    }

    public static List<QuotaGrand> getQuotaGrandListByCode(String grandCode) {
        return quotaGrandMap.get(grandCode);
    }

    public static void setQuotaList(List<Quota> quotaList){
        ServerCacheUtils.quotaList = quotaList;
    }

    public static List<Quota> getQuotaList() {
        return quotaList;
    }

    public static void setStdTableMap(Map<String, StdTable> stdTableMap){
        ServerCacheUtils.stdTableMap = stdTableMap;
    }

    public static StdTable getStdTable(String tableName){
        return stdTableMap != null ? stdTableMap.get(tableName):null;
    }

    public static List<EtlTranRule> getEtlTranRuleListCache() {
        return ServerCacheUtils.etlTranRuleList;
    }

    public static void setEtlTranRuleListCache(List<EtlTranRule> etlTranRuleList) {
        ServerCacheUtils.etlTranRuleList = etlTranRuleList;
    }

    public static List<Menu> getMenuListCache() {
        return ServerCacheUtils.menuListCache;
    }

    public static void setMenuListCache(List<Menu> menuListCache) {
        ServerCacheUtils.menuListCache = menuListCache;
    }

    public static void setRolePermissionCache(Collection<RolePerm> rolePerms) {
        ServerCacheUtils.rolePermCache = rolePerms.stream().collect(Collectors.toMap(RolePerm::getId, rolePerm -> rolePerm));
    }

    /**
     * 根据roleId查询RolePerm
     *
     * @param roleId roleId
     * @return RolePerm
     */
    public static RolePerm getRolePermByRoleId(Long roleId) {
        return rolePermCache.get(roleId);
    }

    public static void setContextPathCache(String contextPath) {
        ServerCacheUtils.contextPath = contextPath;
    }

    /**
     * 从缓存获取contextPath
     *
     * @return contextPath
     */
    public static String getContextPath() {
        return contextPath;
    }

    public static void setAreas(List<Area> areas) {
        ServerCacheUtils.areaCache = areas;
    }

    /**
     * 根据AreaId查询Area
     *
     * @param areaId
     * @return
     */
    public static Area getAreaById(Long areaId) {
        List<Area> collect = ServerCacheUtils.areaCache.stream().filter(area -> area.getId().equals(areaId)).collect(Collectors.toList());
        return collect.size() > 0 ? collect.get(0) : null;
    }

    /**
     * 根据AreaName查询Area
     *
     * @param areaName
     * @return
     */
    public static Area getAreaByName(String areaName) {
        List<Area> collect = ServerCacheUtils.areaCache.stream().filter(area -> area.getAreaName().equals(areaName)).collect(Collectors.toList());
        return collect.size() > 0 ? collect.get(0) : null;
    }
}
