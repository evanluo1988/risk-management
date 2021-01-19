package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Task;
import com.springboot.model.TaskPendingListModel;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.vo.TaskDetailVo;
import com.springboot.vo.TaskImportVo;
import com.springboot.vo.TaskPageVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TaskService extends IService<Task> {

    public void importTasks(MultipartFile file);

    public Pagination<TaskPageVo> findTasks(PageIn pageIn);

    void importTasks0(List<TaskImportVo> data);

    Pagination<TaskPageVo> pageTasks(String disposalStage, LocalDate taskTimeStart, LocalDate taskTimeEnd, Boolean overdue,
                                     LocalDate taskExpireStart, LocalDate taskExpireEnd, String enterpriseName,
                                     String checkStatus , String assignment, Long areaId, Integer pageNo, Integer pageSize);

    TaskDetailVo detail(Long id);

    void del(Long id);

    void dispatcher(Long id, Long areaId);

    void goBack(Long id, String refundReason);

    void revoke(Long id);

    void check(Long id, TaskPageVo taskVo);

    void recheck(Long id);

    Collection<Task> listTaskByTaskNumbers(Set<String> taskNumbers);

    void export(String disposalStage, LocalDate taskTimeStart, LocalDate taskTimeEnd, Boolean overdue, LocalDate taskExpireStart, LocalDate taskExpireEnd, String enterpriseName, String checkStatus, String assignment, Long areaId) throws IOException;

    TaskPendingListModel pendingList();
}
