package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Inform;
import com.springboot.vo.InformImportVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
public interface InformService extends IService<Inform> {

    /**
     * 导入举报信息
     * @param file
     */
    void importInforms(MultipartFile file);

    /**
     * 导入举报信息
     * @param data
     */
    void importInforms0(InformImportVo data);
}
