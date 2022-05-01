package com.thciwei.proj.task;

import com.thciwei.proj.bean.Employee;
import com.thciwei.proj.bean.MailConstants;
import com.thciwei.proj.bean.MailSendLog;
import com.thciwei.proj.service.EmployeeService;
import com.thciwei.proj.service.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MailSendTask {
    @Autowired
    MailSendLogService mailSendLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    EmployeeService employeeService;
   @Scheduled(cron = "0/10 * * * * ?")
    public void mailResendTask(){
       List<MailSendLog> logs= mailSendLogService.getMailSendLogsByStatus();
       if (logs == null || logs.size() == 0) {
           return;
       }
       logs.forEach(mailSendLog -> {
           if(mailSendLog.getCount() >=3){
               mailSendLogService.updateMailSendLogStatus(mailSendLog.getMsgId(),2);
           }else{
               mailSendLogService.updateCount(mailSendLog.getMsgId(), new Date());
               Employee emp = employeeService.getEmployeeById(mailSendLog.getEmpId());
               rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(mailSendLog.getMsgId()));
           }
       });
   }
}
