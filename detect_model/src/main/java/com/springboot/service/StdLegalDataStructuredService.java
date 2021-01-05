package com.springboot.service;

import com.springboot.domain.StdLegalDataStructured;

import java.util.List;

public interface StdLegalDataStructuredService {
    public List<StdLegalDataStructured> findStdLegalDataStructuredByReqId(String reqId);
    public void delete(StdLegalDataStructured stdLegalDataStructured);
}
