package com.springboot.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserLoginCache {
    private static Map<String, List<LocalDateTime>> userLoginMap = Maps.newConcurrentMap();
    private static Map<String, LocalDateTime> userLockMap = Maps.newConcurrentMap();
    private static Object login = new Object();

    public static void lock(String loginName) {
        userLoginMap.putIfAbsent(loginName, Lists.newArrayList());
        synchronized (login) {
            //如果集合在5分钟之内，大于等于3次，就锁用户，
            List<LocalDateTime> localDateTimes = userLoginMap.computeIfPresent(loginName, (k, v) -> {
                if(CollectionUtils.isEmpty(v)) {
                    v = Lists.newArrayList();
                }
                v.add(LocalDateTime.now());
                Iterator<LocalDateTime> iterator = v.iterator();
                while(iterator.hasNext()){
                    if(iterator.next().plusMinutes(5L).isBefore(LocalDateTime.now())) {
                        iterator.remove();
                    }
                }
                return v;
            });
            if(localDateTimes.size() == 3){
                userLockMap.put(loginName, DateUtils.addMinute(10L));
            }
        }
    }

    /**
     * 判断用户是否已被锁
     * @param loginName
     * @return
     */
    public static boolean isLock(String loginName) {
        //userLockMap有值，且当前时间在锁时间之前
        LocalDateTime lockTime = userLockMap.computeIfPresent(loginName, (k,v) -> {
             return Objects.nonNull(v) && LocalDateTime.now().isBefore(v) ? v : null;
        });
         return lockTime != null;
    }
}
