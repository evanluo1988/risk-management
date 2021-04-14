package com.springboot.controller;

import com.springboot.ret.ReturnT;
import com.springboot.utils.ReturnTUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhf
 * @date 2021/4/1 4:24 下午
 **/
@RestController
@RequestMapping("/inform/message")
public class InformMessageController {

    @PutMapping("/comment/{informId}")
    public ReturnT comment(@PathVariable("informId") Long informId){
        // TODO: 2021/4/1
        return ReturnTUtils.newCorrectReturnT();
    }
}
