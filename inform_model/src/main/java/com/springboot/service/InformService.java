package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Area;
import com.springboot.domain.Inform;
import com.springboot.page.Pagination;
import com.springboot.vo.InformImportVo;
import com.springboot.vo.InformPageVo;
import com.springboot.vo.InformViewVo;
import com.springboot.vo.InformVo;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collection;

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
     * 分派
     * @param id
     */
    void dispatcher(Long id);

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
    void check(Long id, InformVo informVo);

    /**
     * 投诉举报列表
     * @param source    举报来源
     * @param checkStatus   核查状态
     * @param informTimeStart   举报时间
     * @param informTimeEnd 举报时间
     * @param rewardContent 奖励情况
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
    Pagination<InformPageVo> informPage(String source, String checkStatus, LocalDate informTimeStart, LocalDate informTimeEnd, String rewardContent, String informName, String verification, Boolean overdue, LocalDate checkTimeStart, LocalDate checkTimeEnd, Long areaId, Integer pageNo, Integer pageSize);

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
}
