package com.springboot.model;

import com.springboot.domain.risk.*;
import lombok.Data;

import java.util.List;

@Data
public class RemoteDataModel {
    List<EdsGsBasic> basicList;
    List<EdsGsQygdjczxx> shareholderList;
    List<EdsGsQyzyglry> personList;
    List<EdsGsQydwtzxx> entInvItemList;
    List<EdsGsFddbrdwtzxx> frInvList;
    List<EdsGsFddbrzqtqyrzxx> frPositionList;
    List<EdsGsFzjgxx> filiationList;
    //List<> liquidationList
    List<EdsGsQylsbgxx> alterList;


    List<EdsGsGqdjlsxx> sharesFrostList;
    //List<> caseInfoList

    List<EdsGsDcdywxx> morguaInfoList;
}
