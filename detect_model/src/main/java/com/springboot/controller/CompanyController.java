package com.springboot.controller;

import com.springboot.dto.CompanyPageOutputDto;
import com.springboot.dto.CompanyPageQueryDto;
import com.springboot.exception.ServiceException;
import com.springboot.order.Sortable;
import com.springboot.page.Pagination;
import com.springboot.ret.ReturnT;
import com.springboot.service.CompanyService;
import com.springboot.utils.ReturnTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lhf
 * @date 2021/3/1 3:56 下午
 **/
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/import")
    public ReturnT importCompany(MultipartFile file){
        companyService.importCompany(file);
        return ReturnTUtils.newCorrectReturnT();
    }


    @GetMapping("/detectCompany")
    public ReturnT detectCompany(){
        companyService.detect();
        return ReturnTUtils.newCorrectReturnT();
    }

    @GetMapping("/page")
    public ReturnT<Pagination<CompanyPageOutputDto>> pageCompany(CompanyPageQueryDto query, Sortable sortable){
        sortable.checkColumn(CompanyPageOutputDto.class);

        Pagination<CompanyPageOutputDto> pagination = companyService.pageCompany(query,sortable);
        return ReturnTUtils.getReturnT(pagination);
    }

    @GetMapping("/streets")
    public ReturnT<List<String>> streets(){
        List<String> streets = companyService.streets();
        return ReturnTUtils.getReturnT(streets);
    }
}
