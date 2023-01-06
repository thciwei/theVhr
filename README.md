# theVhr
#### 项目介绍

微人事，**后端脚手架** ，使用人事管理系统的业务进行实践。Web端使用 **Vue** + **ElementUi** ，后端使用 **SpringBoot** + **Mybatis** 进行开发，使用 **RabbitMQ** 完成了邮件发送模块，使用 **Websocket** 实现了用户聊天，图片存储选择了 **FastDFS**分布式文件系统

#### 项目目录

* kfkserver 负责CRUD和登录认证
* mailserver **邮件发送模块**，新员工入职自动发送入职邮件
* springboot_vhr 后台管理界面

SPA应用进行了整体的maven模块化改造
```
kfkserver结构如下：
-- kfk-mapper
-- kfk-service
-- kfk-web
-- kfk-model
```
#### 项目展示
![](http://tva1.sinaimg.cn/large/006yRl4Gly1h2e50nsg0sj31gc0oatn9.jpg)

![](http://tva1.sinaimg.cn/large/006yRl4Gly1h2e51bodl2j31gf0otgqs.jpg)

![](http://tva1.sinaimg.cn/large/006yRl4Gly1h2e51p93pzj31gi0ovdm5.jpg)



