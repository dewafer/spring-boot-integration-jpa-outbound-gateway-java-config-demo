## JpaOutboundGateway with Spring Boot and Spring Integration using Java Config Demo Project

使用Spring Boot以及Spring Integration构建的Jpa持久化Integration flow演示项目（使用Java配置）

### 使用方法

1. `mvn spring-boot:run`
2. `curl -X POST -H 'Content-Type: text/plain' -d "这条信息将被持久化" http://localhost:8080`
3. 浏览器访问[http://localhost:8080/h2-console](http://localhost:8080/h2-console)
4. 使用以下参数连接数据库，可以看到通过curl发送的消息已经被持久化到`messages`表中去了。
    * Driver Class: `org.h2.Driver`
    * JDBC URL: `jdbc:h2:mem:testdb`
    * User Name: `sa`
    * Password: 留空

### 说明

* JpaOutboundGateway需要有事务支持才能正常进行持久化操作。
* XML配置方式请参考官方文档。
* Java配置方式参照[http://stackoverflow.com/a/39308078/5659614](http://stackoverflow.com/a/39308078/5659614)
* 配置文件请查看`com.dewafer.demo.config.IntegrationConfig`
