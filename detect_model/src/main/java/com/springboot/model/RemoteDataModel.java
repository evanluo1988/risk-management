package com.springboot.model;

import com.springboot.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class RemoteDataModel {
    List<EntWyBasic> basicList;
    List<EntWyShareholder> shareholderList;
    List<EntWyPerson> personList;
    List<EntWyEntinvitem> entInvItemList;
    List<EntWyFrinv> frInvList;
    List<EntWyFrposition> frPositionList;
    List<EntWyFiliation> filiationList;
    List<EntWyLiquidation> liquidationList;
    List<EntWyAlter> alterList;
    List<EntWyMortgagebasic> mortgageBasicList;
    List<EntWyMortgagereg> mortgageRegList;
    List<EntWyMortgagepawn> mortgagePawnList;
    List<EntWyMortgagealt> mortgageAltList;
    List<EntWyMortgagecan> mortgageCanList;
    List<EntWyMortgagedebt> mortgageDebtList;
    List<EntWyMortgageper> mortgagePerList;
    List<EntWyStockpawn> stockPawnList;
    List<EntWyStockpawnalt> stockPawnAltList;
    List<EntWyStockpawncan> stockPawnCanList;
    List<EntWyCaseinfo> caseInfoList;
    List<EntWyException> exceptionList;

}
