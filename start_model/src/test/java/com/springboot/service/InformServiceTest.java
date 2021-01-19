package com.springboot.service;

import com.google.common.collect.Lists;
import com.springboot.ApplicationTest;
import com.springboot.mapper.InformDao;
import com.springboot.model.InformGraphModel;
import com.springboot.service.impl.InformServiceImpl;
import com.springboot.vo.RegUserVo;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InformServiceTest extends ApplicationTest {

    @Autowired
    private InformDao informDao;

    @Test
    public void testGetInformGraphList() {
        List<Long> list = Lists.newArrayList();
        list.add(101L);
        list.add(1L);

        //Mockito.when(areaService.findAreaIdsById(Mockito.anyLong())).thenReturn(list);
        List<InformGraphModel> informGraphModelList = informDao.getInformGraphList(list);
        informGraphModelList.size();
    }
}
