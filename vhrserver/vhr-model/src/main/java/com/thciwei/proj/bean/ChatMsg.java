package com.thciwei.proj.bean;

import lombok.Data;

import java.util.Date;
@Data
public class ChatMsg {
    private String from;
    private String to;
    private String content;
    private Date date;
    private String fromNickname;

}
