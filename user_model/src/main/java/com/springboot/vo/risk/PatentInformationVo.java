package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

/**
 * 专利信息
 */
@Data
public class PatentInformationVo {
    /**
     * 无效专利总数
     */
    private String invalidPatentNum;
    /**
     * 无效发明专利
     */
    private String invalidInventionPatentNum;
    /**
     * 无效新型专利
     */
    private String invalidNewPatentNum;
    /**
     * 无效外观专利
     */
    private String invalidAppearancePatentNum;
    /**
     * 在审专利总数
     */
    private String ontrialPatentNum;
    /**
     * 在审发明专利
     */
    private String ontrialInventionPatentNum;
    /**
     * 在审新型专利
     */
    private String ontrialNewPatentNum;
    /**
     * 在审外观专利
     */
    private String ontrialAppearancePatentNum;
    /**
     * 有效专利总数
     */
    private String validPatentNum;
    /**
     * 有效发明专利
     */
    private String validInventionPatentNum;
    /**
     * 有效新型专利
     */
    private String validNewPatentNum;
    /**
     * 有效外观专利
     */
    private String validAppearancePatentNum;
    /**
     * 转入专利数
     */
    private String transferPatentNum;
    /**
     * 发明人总数
     */
    private String inventorNum;
    /**
     * 自主研发专利数
     */
    private String independentDevelopmentPatentNum;
    /**
     * 专利明细
     */
    private List<StdIaPartentVo> stdIaPartentList;

}

