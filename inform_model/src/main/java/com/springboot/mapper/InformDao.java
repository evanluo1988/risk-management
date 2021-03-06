package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.domain.Inform;
import com.springboot.model.InformExportModel;
import com.springboot.model.InformGraphModel;
import com.springboot.model.InformPageModel;
import com.springboot.model.InformTop10Model;
import com.springboot.vo.InformPageVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
public interface InformDao extends BaseMapper<Inform> {

    /**
     * 投诉举报列表
     * @param checkStatus   核查状态
     * @Param assignment    分派状态
     * @param informTimeStart   举报时间
     * @param informTimeEnd 举报时间
     * @param rewardStatus 奖励情况
     * @param informName    被举报对象名称
     * @param verification  线索属实性审核
     * @param overdue   是否逾期
     * @param checkTimeStart    核查时间
     * @param checkTimeEnd  核查时间
     * @param areaIds   可以查询的地区
     * @param informPage 分页
     * @return  分页的投诉举报
     */
    Page<InformPageModel> informPage(
            @Param("checkStatus") String checkStatus,
            @Param("assignment") String assignment,
            @Param("informTimeStart") LocalDate informTimeStart,
            @Param("informTimeEnd") LocalDate informTimeEnd,
            @Param("rewardStatus") String rewardStatus,
            @Param("informName") String informName,
            @Param("verification") String verification,
            @Param("overdue") Boolean overdue,
            @Param("checkTimeStart") LocalDate checkTimeStart,
            @Param("checkTimeEnd") LocalDate checkTimeEnd,
            @Param("areaIds") List<Long> areaIds,
            Page<Inform> informPage);

    List<InformExportModel> listInformByIds(@Param("ids") List<Long> ids);

    List<InformTop10Model> informsTop10(@Param("areaIds") List<Long> areaIds);

    Integer pendingOverdueList(@Param("areaIds") List<Long> areaIds);

    Integer pendingToCheckList(@Param("areaIds") List<Long> areaIds);
    List<InformGraphModel> getInformGraphList(@Param("areaIds") List<Long> areaIds, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
}
