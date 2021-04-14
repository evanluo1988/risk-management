package com.springboot.controller;


import com.google.common.collect.Lists;
import com.springboot.domain.InformRefund;
import com.springboot.page.Pagination;
import com.springboot.ret.ReturnT;
import com.springboot.service.InformProcessService;
import com.springboot.service.InformRefundService;
import com.springboot.service.InformService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.*;
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
    @Autowired
    private InformProcessService informProcessService;

    /**
     * 举报列表
     * @param informVo
     * @return
     */
    @GetMapping("/list")
    public ReturnT page(InformVo informVo) {
        Pagination<InformPageVo> pagination = informService.informPage(
                informVo.getCheckStatus(),
                informVo.getAssignment(),
                informVo.getInformTimeStart(), informVo.getInformTimeEnd(),
                informVo.getRewardStatus(), informVo.getInformName(),
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
     * 查看中间过程内容
     * @param id
     * @param processId
     * @return
     */
    @GetMapping("/view/{id}/{processId}")
    public ReturnT viewOnProcess(@PathVariable("id") Long id,
                                 @PathVariable("processId") Long processId){
        InformViewVo informViewVo = informService.viewOnProcess(id,processId);
        return ReturnTUtils.getReturnT(informViewVo);
    }

    /**
     * 举报退回列表
     * @param informId
     * @return
     */
    @GetMapping("/refund/list/{informId}")
    public ReturnT refundList(@PathVariable("informId")Long informId){
        List<InformRefundOutputVo> refundList = informRefundService.listRefundByInformId(informId);
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
     * @param informVo
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(InformVo informVo) throws IOException {
        informService.export(
                informVo.getCheckStatus(), informVo.getAssignment(),
                informVo.getInformTimeStart(), informVo.getInformTimeEnd(),
                informVo.getRewardStatus(), informVo.getInformName(),
                informVo.getVerification(), informVo.getOverdue(),
                informVo.getCheckTimeStart(), informVo.getCheckTimeEnd(),
                informVo.getAreaId());
    }

    /**
     * 举报分派（手动）
     * @param id
     * @return
     */
    @PutMapping("/dispatcher/{id}")
    @Validated({InformDispatcherVo.DispatcherGroup.class})
    public ReturnT dispatcher(@PathVariable("id") Long id, @RequestBody @Valid InformDispatcherVo informVo) {
        informService.dispatcher(id, informVo.getAreaId(),informVo.getOpMessage());
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 举报分派（自动批量）
     * @param ids
     * @return
     */
    @PutMapping("/dispatcher/batch")
    public ReturnT dispatcherBatch(@RequestBody Set<Long> ids){
        List<String> msgList = Lists.newArrayList();
        for (Long id : ids) {
            try{
                String r = informService.dispatcher(id, null,null);
                if(r != null){
                    msgList.add(r);
                }
            }catch (Exception e){
                log.error("下发异常",e);
                msgList.add(e.getMessage());
            }
        }
        return ReturnTUtils.getReturnT(msgList);
    }

    /**
     * 举报退回
     * @param id
     * @param informVo
     * @return
     */
    @Validated(InformRefundInputVo.RefundGroup.class)
    @PutMapping("/return/{id}")
    public ReturnT goBack(@PathVariable("id") Long id,
                          @RequestBody @Valid InformRefundInputVo informVo) {
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
                         @RequestBody InformCheckReqVo informVo) {
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

    @GetMapping("/process/list/{id}")
    public ReturnT<List<InformProcessVo>> processList(@PathVariable("id") Long id){
        List<InformProcessVo> taskProcessVoList = informProcessService.listProcess(id);
        return ReturnTUtils.getReturnT(taskProcessVoList);
    }
}
