package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.domain.Task;
import com.springboot.model.TaskExportModel;
import com.springboot.model.TaskModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskMapper extends BaseMapper<Task> {
    IPage<TaskModel> findPageTasks(Page page);

    Page<TaskModel> pageTasks(
            @Param("disposalStage") String disposalStage,
            @Param("taskTimeStart") LocalDate taskTimeStart,
            @Param("taskTimeEnd") LocalDate taskTimeEnd,
            @Param("overdue") Boolean overdue,
            @Param("taskExpireStart") LocalDate taskExpireStart,
            @Param("taskExpireEnd") LocalDate taskExpireEnd,
            @Param("enterpriseName") String enterpriseName,
            @Param("checkStatus") String checkStatus,
            @Param("assignment") String assignment,
            @Param("areaIds") List<Long> areaIds,
            Page<Task> page);

    List<TaskExportModel> listTaskByIds(@Param("ids") List<Long> ids);
}
