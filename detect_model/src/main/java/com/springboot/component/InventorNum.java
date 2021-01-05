package com.springboot.component;

import com.springboot.domain.StdEntBasic;
import com.springboot.domain.StdIaPartent;
import com.springboot.service.StdEntBasicService;
import com.springboot.service.StdIaPartentService;
import com.springboot.util.Utils;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("InventorNum")
public class InventorNum implements QuotaComponent{
    @Autowired
    private StdIaPartentService stdIaPartentService;
    @Autowired
    private StdEntBasicService stdEntBasicService;

    @Override
    public String execQuota(String reqId) {
        StdEntBasic stdEntBasic = stdEntBasicService.getStdEntBasicByReqId(reqId);
        Set<String> intersection = null;
        List<StdIaPartent> stdIaPartentList = stdIaPartentService.findByReqId(reqId);
        stdIaPartentList = Utils.getList(stdIaPartentList).stream().filter(item -> !StringUtils.isEmpty(item.getIno()) && item.getApc().contains(stdEntBasic.getEntName())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(stdIaPartentList)) {
            return "0";
        }

        int index = 0;
        for(StdIaPartent stdIaPartent : stdIaPartentList) {
            if(index == 0) {
                intersection = getInoSet(stdIaPartent);
            } else {
                intersection.retainAll(getInoSet(stdIaPartent));
            }
            index++;
        }

        return intersection.size()+"";
    }

    public Set<String> getInoSet(StdIaPartent stdIaPartent) {
        return stdIaPartent.getIno() != null ?
                new HashSet<>(Arrays.asList(stdIaPartent.getIno().split(";"))) : Sets.newHashSet();
    }
}
