package com.springboot.service;

import com.springboot.domain.risk.StdLegalCasemedian;
import com.springboot.domain.risk.StdLegalCasemedianTemp;

import java.util.List;

public interface StdLegalCasemedianService {
    List<StdLegalCasemedian> findStdLegalCasemedianBySerialNos(String reqId,List<String> serialNos);
}
