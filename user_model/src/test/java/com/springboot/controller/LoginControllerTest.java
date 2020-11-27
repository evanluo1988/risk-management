package com.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.ApplicationTest;
import com.springboot.vo.RegUserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @Author 刘宏飞
 * @Date 2020/11/24 14:08
 * @Version 1.0
 */
@Slf4j
public class LoginControllerTest extends ApplicationTest {
    @Test
    public void testLogin() throws Exception {
        RegUserVo body = new RegUserVo();
        body.setUserName("sysadmin");
        body.setPassword("123456");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(body)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        log.info("login test result : {}",content);
    }
}
