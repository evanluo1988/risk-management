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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Validated
@RestController
@RequestMapping("/informs")
public class InformController {

    @Autowired
    private InformService informService;
    @Autowired
    private InformRefundService informRefundService;

    @GetMapping("/page")
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

    @GetMapping("/view/{id}")
    public ReturnT view(@PathVariable("id") Long id){
        InformViewVo informViewVo = informService.view(id);
        return ReturnTUtils.getReturnT(informViewVo);
    }

    @GetMapping("/refund/list/{informId}")
    public ReturnT refundList(@PathVariable("informId")Long informId){
        List<InformRefund> refundList = informRefundService.listRefundByInformId(informId);
        return ReturnTUtils.getReturnT(refundList);
    }

    @PostMapping("/import")
    public ReturnT importInforms(MultipartFile file) {
        informService.importInforms(file);
        return ReturnTUtils.newCorrectReturnT();
    }

    @Validated(InformVo.DispatcherGroup.class)
    @PutMapping("/dispatcher/{id}")
    public ReturnT dispatcher(@PathVariable("id") Long id,
                              @RequestBody @Valid InformVo informVo) {
        informService.dispatcher(id, informVo.getAreaId());
        return ReturnTUtils.newCorrectReturnT();
    }

    @PutMapping("/return/{id}")
    public ReturnT goBack(@PathVariable("id") Long id,
                          @RequestBody InformVo informVo) {
        informService.goBack(id,informVo.getRefundReason());
        return ReturnTUtils.newCorrectReturnT();
    }

    @PutMapping("/check/{id}")
    public ReturnT check(@PathVariable("id") Long id,
                         @RequestBody InformVo informVo) {
        informService.check(id, informVo);
        return ReturnTUtils.newCorrectReturnT();
    }

    @DeleteMapping("/del/{id}")
    public ReturnT del(@PathVariable("id") Long id){
        informService.del(id);
        return ReturnTUtils.newCorrectReturnT();
    }
}
