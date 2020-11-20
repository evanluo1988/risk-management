package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.springboot.config.MyMetaObjectHandler;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zx on 2020/7/25.
 */
public class BaseDomain implements Serializable {

    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    private String createPerson;
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    private Date modifyDate;
    private String modifyPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }
}
