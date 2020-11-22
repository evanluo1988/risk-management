package com.springboot.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import java.util.Date;

/**
 * Created by zx on 2020/7/25.
 * This use for auto fill create time and update time when insert or update in DB
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //this.strictInsertFill(metaObject, "createDate", Date.class, new Date());
        //this.fillStrategy(metaObject, "createDate", new Date());
        this.setFieldValByName("createDate", new Date(), metaObject);
        this.setFieldValByName("modifyDate", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //this.strictUpdateFill(metaObject, "modifyDate", Date.class, new Date());
        //this.fillStrategy(metaObject, "modifyDate", new Date());
        this.setFieldValByName("modifyDate", new Date(), metaObject);
    }
}
