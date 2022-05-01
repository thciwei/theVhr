package com.thciwei.proj.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@ApiModel(value = "职称实体类")
public class JobLevel implements Serializable {
    private Integer id;

    private String name;

    private String titleLevel;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/shanghai")
    private Date createDate;

    private Boolean enabled;

    public JobLevel(String name) {
        this.name = name;
    }

    public JobLevel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        JobLevel jobLevel = (JobLevel) o;
        return Objects.equals(name, jobLevel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

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

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}