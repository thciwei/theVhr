package com.thciwei.proj.bean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "用户角色实体类")
public class Role {
    private Integer id;

    private String name;

    private String nameZh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String namezh) {
        this.nameZh = namezh == null ? null : namezh.trim();
    }
}