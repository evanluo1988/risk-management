package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.InformMessage;
import com.springboot.mapper.InformMessageDao;
import com.springboot.service.InformMessageService;
import org.springframework.stereotype.Service;

/**
 * @author lhf
 * @date 2021/4/1 4:22 下午
 **/
@Service
public class InformMessageServiceImpl extends ServiceImpl<InformMessageDao, InformMessage> implements InformMessageService {
}
