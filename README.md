# sodo-platform

## 项目简介

* **IBAC（Interface-Based Access Control，基于接口的权限控制）微服务快速开发平台**
* **面向多客户端的客户端配置中心**
* **客户端启用管理、账号并发登录、登录回调、开放注册、令牌失效时间以及令牌共享**
* **客户端接口防盗链、接口调用统计、接口限流**
* **客户端 IP 黑名单、IP 白名单**
* **用户登录信息加密**
* **用户一键下线**
* **GitHub + Jenkins + Docker + Harbor 的自动化集成与部署方案**
* **...**

**系统后台前端仓库：** https://github.com/irvingsoft/sodo-housekeeper-view-web.git

**体验地址：** https://housekeeper.sodo.cool

**用户名：** admin

**密码：** 111111

    由于原仓库错误的合并操作，故清空后重新提交了代码。

<img alt="原仓库概况图" src="https://images.gitee.com/uploads/images/2021/0813/163259_993d88d7_7701512.png">

## 平台架构

<img alt="平台架构图" src="./doc/平台架构.png">

    sodo-platform
    ├─doc 					            // 文档
    ├─sodo-auth 			            // 统一认证服务
    ├─sodo-catkin 				        // 分布式 ID 服务（整合自 TinyId）
    ├─sodo-common 					    // 公共总包
    │  ├─sodo-catkin-starter 		    // 分布式 ID 客户端
    │  ├─sodo-common-base 			    // 公共基础包
    │  ├─sodo-common-core 		        // 公共核心包
    │  ├─sodo-common-starter 	        // 公共客户端
    │  ├─sodo-knife4j-starter 	        // Swagger2 接口文档客户端
    │  ├─sodo-log-starter 		        // 分布式日志客户端
    │  ├─sodo-mybatis-starter 	        // Mybatis-plus 客户端
    │  ├─sodo-openfeign-starter         // Openfeign 客户端
    │  ├─sodo-rabbitmq-starter 		    // RabbitMq 客户端
    │  └─sodo-redis-starter 	        // Redis 客户端
    ├─sodo-eureka 					    // 注册中心服务
    ├─sodo-goods 				    	// 业务模块-商品服务（待开发）
    ├─sodo-housekeeper 			    	// 管家服务
    ├─sodo-log 			                // 日志服务
    ├─sodo-order 					    // 业务模块-订单服务（待开发）
    ├─sodo-payment 					    // 业务模块-支付服务（待开发）
    ├─sodo-user 					    // 用户服务
    └─sodo-zuul 					    // 路由网关服务

## 基于接口的权限控制（IBAC）

基于接口的权限控制从两个方面进行权限控制：

1. 控制接口所属客户端，没有加入接口对应的客户端列表的客户端无法请求该接口。
2. 在基于角色的权限控制基础上，将权限细化到每个接口。当请求的接口存在权限标识时，查询用户对应角色拥有的权限，并进行权限匹配。

<img alt="IBAC 架构图" src="doc/基于接口的权限控制.png">

## 启动流程

```
1. 使用 MySQL 新建数据库 sodo_platform
2. 运行 doc/sodo_platform.sql 脚本
3. 安装 Redis
4. 安装 RabbitMq
5. 更换每个服务中的 src/main/resources/application-dev.yml 中的数据库连接为本地 MySQL 的用户名和密码
6. 更换每个服务中的 src/main/resources/application-dev.yml 中的 RabbitMq 连接为本地 RabbitMq 的用户名和密码，默认为 guest/guest
7. 在 Idea 服务配置中添加启动参数 dev
8. 启动各个服务
```

<img alt="Idea 配置启动参数" src="doc/Idea%20配置启动参数.png">
<img alt="Idea Maven 配置" src="doc/Idea%20Maven%20配置.png">

## 应用管理

<img alt="应用管理" src="doc/应用管理.jpg">
<img alt="应用管理-编辑" src="doc/应用管理-编辑.png">

## 多客户端用户管理

<img alt="用户管理" src="doc/用户管理.png">
<img alt="用户管理-编辑" src="doc/用户管理-编辑.png">
<img alt="用户管理-授权" src="doc/用户管理-授权.png">
<img alt="用户管理-踢人下线" src="doc/用户管理-踢人下线.png">

## 多客户端角色管理

<img alt="角色管理" src="doc/角色管理.png">
<img alt="角色管理-授权" src="doc/角色管理-授权.png">

## 多客户端菜单管理

<img alt="菜单管理" src="doc/菜单管理.png">
<img alt="菜单管理-编辑" src="doc/菜单管理-编辑.png">

## 接口管理

<img alt="接口管理" src="doc/接口管理.png">
<img alt="接口管理-编辑" src="doc/接口管理-编辑.png">
<img alt="接口管理-限流" src="doc/接口管理-限流.png">

## 多客户端 IP 管理

<img alt="IP 管理" src="doc/IP 管理.png">

## 分布式日志

<img alt="日志-业务" src="doc/日志-业务.png">
<img alt="日志-接口" src="doc/日志-接口.png">
<img alt="日志-错误" src="doc/日志-错误.png">

## 接口编写规范

* 推荐使用 URL 传参
* RESTFul 接口约定

  `Method`（资源操作行为，改变资源的状态）。

  `GET` ：请求服务器特定资源。

  `POST` ：服务器创建一个新资源。

  `PUT` ：更新服务器资源客（整个资源）。

  `DELETE` ：服务器删除特定资源。

  `PATCH` ：更新服务器上的资源（资源的部分）。

## Swagger2 接口文档地址

127.0.0.1:9511/doc.html
