package com.thciwei.proj.bean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;


@ApiModel(value = "消息实体类",description = "消息的来源去向等")
@Data
public class ChatMsg {
    private String from;
    private String to;
    private String content;
    private Date date;
    private String fromNickname;

}
