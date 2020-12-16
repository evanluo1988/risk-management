package com.springboot.controller;

import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.ret.ReturnT;
import com.springboot.service.TaskService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.TaskDetailVo;
import com.springboot.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/verifies")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/import")
    public ReturnT importInforms(MultipartFile file){
        taskService.importTasks(file);
        return ReturnTUtils.newCorrectReturnT();
    }

    @GetMapping("/page")
    public ReturnT page(TaskVo taskVo){
        Pagination<TaskVo> taskModelPagination = taskService.pageTasks(
                taskVo.getEnterpriseName(),
                taskVo.getCheckStatus(),
                taskVo.getDisposalStage(),
                taskVo.getAssignment(),
                taskVo.getCheckRegion(),
                taskVo.getPageNo(),
                taskVo.getPageSize());
        return ReturnTUtils.getReturnT(taskModelPagination);
    }

    @GetMapping("/view/{id}")
    public ReturnT detail(@PathVariable("id") Long id){
        TaskDetailVo taskDetailVo = taskService.detail(id);
        return ReturnTUtils.getReturnT(taskDetailVo);
    }

    @DeleteMapping("/del/{id}")
    public ReturnT del(@PathVariable("id") Long id){
        taskService.del(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    @Validated(TaskVo.DispatcherGroup.class)
    @PutMapping("/dispatcher/{id}")
    public ReturnT dispatcher(@PathVariable("id") Long id,
                              @RequestBody @Valid TaskVo taskVo){
        taskService.dispatcher(id,taskVo.getAreaId());
        return ReturnTUtils.newCorrectReturnT();
    }

    @Validated(TaskVo.RefundGroup.class)
    @PutMapping("/return/{id}")
    public ReturnT goBack(@PathVariable("id") Long id,
                          @RequestBody @Valid TaskVo taskVo){
        taskService.goBack(id,taskVo.getRefundReason());
        return ReturnTUtils.newCorrectReturnT();
    }

    @PutMapping("/revoke/{id}")
    public ReturnT revoke(@PathVariable("id") Long id){
        taskService.revoke(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    @PutMapping("/check/{id}")
    public ReturnT check(@PathVariable("id") Long id,
                         @RequestBody TaskVo taskVo) {
        taskService.check(id, taskVo);
        return ReturnTUtils.newCorrectReturnT();
    }

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
