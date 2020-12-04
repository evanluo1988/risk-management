package com.springboot.service.remote;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 微云接口
 * @Author 刘宏飞
 * @Date 2020/12/4 10:21
 * @Version 1.0
 */
@FeignClient(value = "wyRemoteService",url = "${wy.base.url}")
public interface WYRemoteService {

    static String calcSign(String businessId, String timeStamp, String appKey) {
        String originStr = businessId+timeStamp+appKey;
        String md5 = DigestUtils.md5Hex(originStr);
        return md5;
    }

    @RequestMapping(value = "/exchange/entry/customer/datacollection",method = RequestMethod.POST)
    CustomerDataCollectionResponse customerDataCollection(@RequestBody CustomerDataCollectionRequest customerDataCollectionRequest);

    @Data
    class CustomerDataCollectionResponse{
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
            private R11C53 R11C53;

            @lombok.Data
            class R11C53{
                private String code;
                private String msg;
                private List<R11C53Data> data;

                @lombok.Data
                class R11C53Data{
                    private List<BasicList> basicList;

                    @lombok.Data
                    class BasicList {
                        private String address;
                    }
                }
            }
        }
    }

    @Data
    @Accessors(chain = true)
    class CustomerDataCollectionRequest{
        private String businessID;
        private String entName;
        private String entCreditID="911112345671234567";
        private String indName="黄日林";
        private String indCertID="uTln8yoWNBcFDHgUv24IXvQTVoGyDvrYzKvAcHzbyhM=";
        private String productCode;
        private String appID;
        private String timestamp;
        private String signature;
    }
}
