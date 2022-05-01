package com.thciwei.proj.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "消息发送实体类",description = "主要用于邮件发送成功的确认")
@Data
public class MailSendLog {
    private String msgId;
    private Integer empId;
    //0 消息投递中   1 投递成功   2投递失败
    private Integer status;
    private String routeKey;
    private String exchange;
    //重试次数
    private Integer count;
    //重试时间
    private Date tryTime;
    private Date createTime;
    private Date updateTime;

}
