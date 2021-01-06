package com.springboot.controller;


import com.springboot.domain.InformRefund;
import com.springboot.page.Pagination;
import com.springboot.ret.ReturnT;
import com.springboot.service.InformRefundService;
import com.springboot.service.InformService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.InformPageVo;
import com.springboot.vo.InformViewVo;
import com.springboot.vo.InformVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/informs")
public class InformController {

    @Autowired
    private InformService informService;
    @Autowired
    private InformRefundService informRefundService;

    /**
     * 举报列表
     * @param informVo
     * @return
     */
    @GetMapping("/list")
    public ReturnT page(InformVo informVo) {
        Pagination<InformPageVo> pagination = informService.informPage(
                informVo.getSource(), informVo.getCheckStatus(),
                informVo.getInformTimeStart(), informVo.getInformTimeEnd(),
                informVo.getRewardContent(), informVo.getInformName(),
                informVo.getVerification(), informVo.getOverdue(),
                informVo.getCheckTimeStart(), informVo.getCheckTimeEnd(),
                informVo.getAreaId(),
                informVo.getPageNo(),informVo.getPageSize());
        return ReturnTUtils.getReturnT(pagination);
    }

    /**
     * 举报查看
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ReturnT view(@PathVariable("id") Long id){
        InformViewVo informViewVo = informService.view(id);
        return ReturnTUtils.getReturnT(informViewVo);
    }

    /**
     * 举报退回列表
     * @param informId
     * @return
     */
    @GetMapping("/refund/list/{informId}")
    public ReturnT refundList(@PathVariable("informId")Long informId){
        List<InformRefund> refundList = informRefundService.listRefundByInformId(informId);
        return ReturnTUtils.getReturnT(refundList);
    }

    /**
     * 举报导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public ReturnT importInforms(MultipartFile file) {
        informService.importInforms(file);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 举报导出
     * @param ids
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(@RequestParam List<Long> ids) throws IOException {
        informService.export(ids);
    }

    /**
     * 举报分派（手动）
     * @param id
     * @return
     */
    @PutMapping("/dispatcher/{id}")
    public ReturnT dispatcher(@PathVariable("id") Long id) {
        informService.dispatcher(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 举报分派（自动批量）
     * @param ids
     * @return
     */
    @PutMapping("/dispatcher/batch")
    public ReturnT dispatcherBatch(@RequestBody Set<Long> ids){
        for (Long id : ids) {
            try{
                informService.dispatcher(id);
            }catch (Exception e){
                log.error("下发异常",e);
            }
        }
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 举报退回
     * @param id
     * @param informVo
     * @return
     */
    @Validated(InformVo.RefundGroup.class)
    @PutMapping("/return/{id}")
    public ReturnT goBack(@PathVariable("id") Long id,
                          @RequestBody @Valid InformVo informVo) {
        informService.goBack(id,informVo.getRefundReason());
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 举报撤回
     * @param id
     * @return
     */
    @PutMapping("/revoke/{id}")
    public ReturnT revoke(@PathVariable("id") Long id){
        informService.revoke(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 举报核查
     * @param id
     * @param informVo
     * @return
     */
    @PutMapping("/process/{id}")
    public ReturnT check(@PathVariable("id") Long id,
                         @RequestBody InformVo informVo) {
        informService.check(id, informVo);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 重新审核
     * @param id
     * @return
     */
    @PutMapping("/recheck/{id}")
    public ReturnT recheck(@PathVariable("id") Long id){
        informService.recheck(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 举报删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ReturnT del(@PathVariable("id") Long id){
        informService.del(id);
        return ReturnTUtils.newCorrectReturnT();
    }
}
