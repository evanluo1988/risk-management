package com.springboot.model;

import com.springboot.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class TaskImportModel {
    private Task task;
    private List<TaskCheck> taskCheckList;
    private List<Enterprise> enterpriseList;
    private List<TaskDisposition> taskDispositionList;
}
