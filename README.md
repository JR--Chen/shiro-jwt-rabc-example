# shiro-jwt-rabc-example

环境JDK 1.8 或以上
Mysql5.6或以上
 
修改application.properties中的数据库配置 

在数据库中执行rbac.sql文件生成表

如果需要修改表结构，使用mybatis-generator.xml重新生成mapper

这个仅仅是demo，切勿在生产数据库中存储明文密码。