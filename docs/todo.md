* 数据库设计，各个字段的选用及默认类型
* 数据库的配置 jpa及mysql
* springboot 运行只要运行Application文件即可，不用配置tomcat





According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You nee
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/blog?useUnicode=true&characterEncoding=UTF-8&useSSL=true

*post请求传递对象时候，不要添加注释@RequestBody ，不然会报错
Content type 'multipart/form-data;boundary=----WebKitFormBoundaryTVc9eDC2a2elulOx;charset=UTF-8' not supported



* jpa 自动添加createtime和updatetime
https://www.jishux.com/plus/view-599736-1.html

*SQL [n/a]; nested exception is org.hibernate.exception.DataException: could not execute statement