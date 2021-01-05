package com.springboot.service;

import com.springboot.domain.StdLegalCasemedian;

import java.util.List;

public interface StdLegalCasemedianService {
    List<StdLegalCasemedian> findStdLegalCasemedianBySerialNos(String reqId,List<String> serialNos);
}
