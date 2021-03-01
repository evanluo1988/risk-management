package com.springboot.service.remote;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/3 18:49
 * @Version 1.0
 */
@FeignClient(name = "geoRemoteService", url = "${map.base.url}")
public interface GeoRemoteService {

    @RequestMapping(value = "/geo", method = RequestMethod.GET)
    GeoResponse geo(@RequestParam(value = "key",required =false,defaultValue = "${map.key}") String key,
                    @RequestParam(value = "address") String address);


    @RequestMapping(value = "/regeo",method = RequestMethod.GET)
    ReGeoResponse regeo(@RequestParam(value = "key",required =false,defaultValue = "${map.key}") String key,
                        @RequestParam(value = "location") String location,
                        @RequestParam(value = "extensions",defaultValue = "base") String extensions,
                        @RequestParam(value = "batch",defaultValue = "false") String batch,
                        @RequestParam(value = "roadlevel",defaultValue = "1") String roadlevel);

    @Data
    class GeoResponse {
        private String status;
        private String info;
        private String infocode;
        private String count;
        private List<GeoCode> geocodes;

        public boolean succ(){
            return "OK".equalsIgnoreCase(info);
        }

        @Data
        public static class GeoCode {
            private String location;
        }
    }

    @Data
    class ReGeoResponse{
        private String status;
        private String info;
        private String infocode;
        private ReGeoCode regeocode;

        public boolean succ(){
            return "OK".equalsIgnoreCase(info);
        }
        @Data
        public static class ReGeoCode{
            private AddressComponent addressComponent;
            @Data
            public static class AddressComponent{
                //街道
                private String township;
                // 区
                private String district;
                private StreetNumber streetNumber;
                @Data
                public static class StreetNumber{
                    //街
                    private String street;
                    //号
                    private String number;
                }
            }
        }
    }
}
