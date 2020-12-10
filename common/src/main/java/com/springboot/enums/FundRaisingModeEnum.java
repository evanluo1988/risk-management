package com.springboot.enums;

/**
 * 集资模式
 * @Author 刘宏飞
 * @Date 2020/12/10 14:26
 * @Version 1.0
 */
public enum FundRaisingModeEnum {
    INVESTMENT_AND_FINANCIAL_MANAGEMENT("INVESTMENT_AND_FINANCIAL_MANAGEMENT","投资理财"),
    PRIVATE_LENDING("PRIVATE_LENDING","民间借贷"),
    CONSUMER_REBATE("CONSUMER_REBATE","消费返利"),
    DIGITAL_CURRENCY("DIGITAL_CURRENCY","数字货币"),
    MUTUAL_FUNDING("MUTUAL_FUNDING","资金互助"),
    NEW_PRODUCT_INVESTMENT("NEW_PRODUCT_INVESTMENT","新产品投资"),
    ELDERLY_SERVICE("ELDERLY_SERVICE","养老服务"),
    AFTER_SALE_CHARTER("AFTER_SALE_CHARTER","售后包租"),
    OTHER("OTHER","其他")

    ;
    private String code;
    private String desc;

    FundRaisingModeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static FundRaisingModeEnum descOf(String desc){
        FundRaisingModeEnum[] values = FundRaisingModeEnum.values();
        for (FundRaisingModeEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
