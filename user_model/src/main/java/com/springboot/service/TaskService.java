package com.springboot.service;

import com.springboot.model.TaskImportModel;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.vo.TaskImportVo;
import com.springboot.vo.TaskVo;
import org.springframework.web.multipart.MultipartFile;

public interface TaskService {

    public void importTasks(MultipartFile file);
    void importTask(TaskImportModel data);
    public Pagination<TaskVo> findTasks(PageIn pageIn);
}
