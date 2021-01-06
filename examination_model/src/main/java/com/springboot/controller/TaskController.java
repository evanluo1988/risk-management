package com.springboot.controller;

import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.ret.ReturnT;
import com.springboot.service.TaskService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.TaskDetailVo;
import com.springboot.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@Validated
@RestController
@RequestMapping("/verifies")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 核查导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public ReturnT importInforms(MultipartFile file){
        taskService.importTasks(file);
        return ReturnTUtils.newCorrectReturnT();
    }

    @GetMapping("/list")
    public ReturnT page(String enterpriseName, String checkStatus, String disposalStage,
                        String assignment, String checkRegion, Integer pageNo, Integer pageSize){
//        Pagination<TaskVo> taskModelPagination = taskService.pageTasks(
//                taskVo.getEnterpriseName(),
//                taskVo.getCheckStatus(),
//                taskVo.getDisposalStage(),
//                taskVo.getAssignment(),
//                taskVo.getCheckRegion(),
//                pageIn.getPageNo(),
//                pageIn.getPageSize());
        Pagination<TaskVo> taskModelPagination = taskService.pageTasks(enterpriseName, checkStatus, disposalStage,
                assignment, checkRegion, pageNo, pageSize);
        return ReturnTUtils.getReturnT(taskModelPagination);
    }

    /**
     * 核查查询
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ReturnT detail(@PathVariable("id") Long id){
        TaskDetailVo taskDetailVo = taskService.detail(id);
        return ReturnTUtils.getReturnT(taskDetailVo);
    }

    /**
     * 核查删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ReturnT del(@PathVariable("id") Long id){
        taskService.del(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 核查分派(手动)
     * @param id
     * @return
     */
    @PutMapping("/dispatcher/{id}")
    public ReturnT dispatcher(@PathVariable("id") Long id){
        taskService.dispatcher(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 核查分派(自动批量)
     * @param ids
     * @return
     */
    @PutMapping("/dispatcher/batch")
    public ReturnT dispatcher(@RequestBody Set<Long> ids){
        for (Long id : ids) {
            try {
                taskService.dispatcher(id);
            }catch (Exception e){
                log.error("下发异常：",e);
            }
        }
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 任务退回（由街道办主动退回）
     * @param id
     * @param taskVo
     * @return
     */
    @Validated(TaskVo.RefundGroup.class)
    @PutMapping("/return/{id}")
    public ReturnT goBack(@PathVariable("id") Long id,
                          @RequestBody @Valid TaskVo taskVo){
        taskService.goBack(id,taskVo.getRefundReason());
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 任务撤回（由区级主动撤回）
     * @param id
     * @return
     */
    @PutMapping("/revoke/{id}")
    public ReturnT revoke(@PathVariable("id") Long id){
        taskService.revoke(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 核查
     * @param id
     * @param taskVo
     * @return
     */
    @PutMapping("/process/{id}")
    public ReturnT check(@PathVariable("id") Long id,
                         @RequestBody TaskVo taskVo) {
        taskService.check(id, taskVo);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 重新核查
     * @param id
     * @return
     */
    @PutMapping("/recheck/{id}")
    public ReturnT recheck(@PathVariable("id") Long id){
        taskService.recheck(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    @GetMapping
    public ReturnT<Pagination<TaskVo>> findTaskList(PageIn pageIn){
        return ReturnTUtils.getReturnT(taskService.findTasks(pageIn));
    }

}
