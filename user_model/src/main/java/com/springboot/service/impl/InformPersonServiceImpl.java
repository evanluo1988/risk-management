package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.InformPerson;
import com.springboot.mapper.InformPersonDao;
import com.springboot.service.InformPersonService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Service
public class InformPersonServiceImpl extends ServiceImpl<InformPersonDao, InformPerson> implements InformPersonService {

}
