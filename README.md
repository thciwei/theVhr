# theVhr
#### 项目介绍

**微人事**，一个**前后端分离人事管理系统**。Web端使用 **Vue** + **ElementUi** ，后端使用 **SpringBoot** + **Mybatis** 进行开发，使用 **RabbitMQ** 完成了邮件发送模块，使用 **Websocket** 实现了用户聊天，图片存储选择了 **FastDFS**

在学习的过程中，最重要的是把学到的技术系统的进行整合，这是作者大二时期学习并完成的第一个完整项目，来自江南一点雨的 [微人事](https://github.com/lenve/vhr) 

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

#### 推荐学习

* 江南一点雨松哥的 [微人事](https://github.com/lenve/vhr) 和 适合初学者的 [微部落](https://github.com/lenve/VBlog)

