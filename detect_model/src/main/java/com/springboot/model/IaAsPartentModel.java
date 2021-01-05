package com.springboot.model;

import com.springboot.domain.IaAsPartent;
import lombok.Data;

import java.util.List;

@Data
public class IaAsPartentModel extends IaAsPartent{
    private List<IaAsPartentDetailModel> detailList;
}
