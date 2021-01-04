package com.springboot.model.remote;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

@Data
public class CustomerIndustrialAndJusticeResponse {
    private Integer code;
    private String data;
    private String message;

    public boolean succ(){
        return Integer.valueOf(0).equals(code);
    }

    public Data toData() {
        Data data = JSON.parseObject(this.data, Data.class);
        return data;
    }

    @lombok.Data
    static class Data{
        private Data.R11C53 R11C53;

        @lombok.Data
        class R11C53{
            private String code;
            private String msg;
            private List<Data.R11C53.R11C53Data> data;

            @lombok.Data
            class R11C53Data{
                private List<Data.R11C53.R11C53Data.BasicList> basicList;

                @lombok.Data
                class BasicList {
                    private String address;
                }
            }
        }
    }
}
