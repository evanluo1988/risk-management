package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Inform;
import com.springboot.model.InformTop10Model;
import com.springboot.model.InfromPendingListModel;
import com.springboot.model.InformGraphModel;
import com.springboot.page.Pagination;
import com.springboot.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
public interface InformService extends IService<Inform> {

    /**
     * 导入举报信息
     * @param file
     */
    void importInforms(MultipartFile file);

    /**
     * 导入举报信息
     * @param data
     */
    void importInforms0(Collection<InformImportVo> data);

    /**
     * 导出
     * @param checkStatus
     * @Param assignment
     * @param informTimeStart
     * @param informTimeEnd
     * @param rewardStatus
     * @param informName
     * @param verification
     * @param overdue
     * @param checkTimeStart
     * @param checkTimeEnd
     * @param areaId
     */
    void export(String checkStatus, String assignment, LocalDate informTimeStart, LocalDate informTimeEnd, String rewardStatus, String informName, String verification, Boolean overdue, LocalDate checkTimeStart, LocalDate checkTimeEnd, Long areaId) throws IOException;

    /**
     * 分派
     * @param id
     * @param areaId
     */
    void dispatcher(Long id, Long areaId);

    /**
     * 退回
     * @param id
     * @param refundReason
     */
    void goBack(Long id, String refundReason);

    /**
     * 核查
     * @param id
     * @param informVo
     */
    void check(Long id, InformCheckVo informVo);

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
     * @param areaId    涉及地区
     * @param pageNo    分页
     * @param pageSize  分页
     * @return  分页的投诉举报VO
     */
    Pagination<InformPageVo> informPage(String checkStatus, String assignment, LocalDate informTimeStart, LocalDate informTimeEnd, String rewardStatus, String informName, String verification, Boolean overdue, LocalDate checkTimeStart, LocalDate checkTimeEnd, Long areaId, Integer pageNo, Integer pageSize);

    /**
     * 删除举报
     * @param id
     */
    void del(Long id);

    /**
     *
     * @param id
     * @return
     */
    InformViewVo view(Long id);

    void revoke(Long id);

    void recheck(Long id);

    List<InformTop10Model> informsTop10();

    InfromPendingListModel pendingList();
    /**
     * 举报图
     * @return
     */
    List<InformGraphModel> getInformGraphList(LocalDate startDate, LocalDate endDate);
}
