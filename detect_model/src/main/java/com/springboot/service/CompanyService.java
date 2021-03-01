package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Company;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lhf
 * @date 2021/3/1 3:45 下午
 **/
public interface CompanyService extends IService<Company> {
    /**
     * 导入公司信息
     * @param file
     */
    void importCompany(MultipartFile file);
}
