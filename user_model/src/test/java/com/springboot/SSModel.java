package com.springboot;

import com.springboot.domain.risk.EdsSsSsjghsj;
import lombok.Data;

import java.util.List;

@Data
public class SSModel {
    private String code;
    private String msg;
    private List<EdsSsSsjghsj> data;
}
