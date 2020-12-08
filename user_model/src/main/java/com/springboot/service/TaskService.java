package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Task;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.vo.TaskDetailVo;
import com.springboot.vo.TaskImportVo;
import com.springboot.vo.TaskVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService extends IService<Task> {

    public void importTasks(MultipartFile file);

    public Pagination<TaskVo> findTasks(PageIn pageIn);

    void importTasks0(List<TaskImportVo> data);

    Pagination<TaskVo> pageTasks(String enterpriseName, String checkStatus, String disposalStage, String assignment, String checkRegion, Integer pageNo, Integer pageSize);

    TaskDetailVo detail(Long id);

    void del(Long id);

    void dispatcher(Long id, Long areaId);

    void goBack(Long id, String refundReason);

    void revoke(Long id);

    void check(Long id, TaskVo taskVo);

    void recheck(Long id);
}
