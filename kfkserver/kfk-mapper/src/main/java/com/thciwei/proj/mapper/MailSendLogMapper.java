package com.thciwei.proj.mapper;

import com.thciwei.proj.bean.MailSendLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MailSendLogMapper {


     Integer insert(MailSendLog mailSendLog);


    Integer updateMailSendLogStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    List<MailSendLog> getMailSendLogsByStatus();

    Integer updateCount(@Param("msgId") String msgId,@Param("date") Date date);
}
