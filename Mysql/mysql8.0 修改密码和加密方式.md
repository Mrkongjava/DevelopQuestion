# mysql8.0 修改密码和加密方式





1、登录

mysql -u root -p

2、修改密码

```html
ALTER USER 'root'@'localhost' IDENTIFIED BY '123456' PASSWORD EXPIRE NEVER;
flush privileges;
```

 3、客户端无法连接

Client does not support authentication protocol requested by server; consider upgrading MySQL client 

 select user, host, plugin, authentication_string from user\G;查看加密方式



8.0加密方式（caching_sha2_password）有些客户端还不支持 所以使用mysql_native_password加密

```shell
# 修改某个用户登录时加密方式（暂时没有找到全局修改方式）
ALTER USER 'root'@'localhost' IDENTIFIED with mysql_native_password BY '123456' PASSWORD EXPIRE NEVER;
```

