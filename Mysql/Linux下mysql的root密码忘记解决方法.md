# Linux下mysql忘记root密码解决方法



## 方法1（亲测可用）

1．首先确认服务器出于安全的状态，也就是没有人能够任意地连接MySQL数据库。 因为在重新设置MySQL的root密码的期间，MySQL数据库完全出于没有密码保护的 状态下，其他的用户也可以任意地登录和修改MySQL的信息。可以采用将MySQL对外的端口封闭，并且停止Apache以及所有的用户进程的方法实现服务器的准安全状态。最安全的状态是到服务器的Console上面操作，并且拔掉网线。

2．修改MySQL的登录设置： 首先进入到服务器的/etc文件夹下找到my.cnf文件并打开

```
# vi /etc/my.cnf

在[mysqld]的段中加上一句（亲测时在最后面加上）：skip-grant-tables 保存并且退出vi。
```

3．重新启动mysqld （或者使用：service mysqld restart重启）

```
# /etc/init.d/mysqld restart 
```

4．登录并修改MySQL的root密码

```
mysql> USE mysql ;  
mysql> UPDATE user SET Password = password ( 'new-password' ) WHERE User = 'root' ; 
mysql> flush privileges ;  
mysql> quit
```

5．将MySQL的登录设置修改回来

```
# vi /etc/my.cnf
```

将刚才在[mysqld]的段中加上的skip-grant-tables删除 

保存并且退出vi。



6．重新启动mysqld 

```
# /etc/init.d/mysqld restart  ( service mysqld restart )
```

7．恢复服务器的正常工作状态

将步骤一中的操作逆向操作。恢复服务器的工作状态。 



## 方法2

如果忘记了MySQL的root密码，可以用以下方法重新设置：

1. KILL掉系统里的MySQL进程； 

   killall -TERM mysqld

   

2. 用以下命令启动MySQL，以不检查权限的方式启动； 

     safe_mysqld --skip-grant-tables &

   

3. 然后用空密码方式使用root用户登录 MySQL； 

   mysql -u root

   

4. 修改root用户的密码； 

   ```
   mysql> update mysql.user set password=PASSWORD('新密码') where User='root'; 
   mysql> flush privileges;  
   mysql> quit
   ```

   重新启动MySQL，就可以使用新密码登录了

   

## 方法3

有可能你的系统没有 safe_mysqld 程序(比如我现在用的 ubuntu操作系统, apt-get安装的mysql) , 下面方法可以恢复

1. 停止mysqld（(您可能有其它的方法,总之停止mysqld的运行就可以了)）

   /etc/init.d/mysql stop

   

2. 用以下命令启动MySQL，以不检查权限的方式启动； 

   mysqld --skip-grant-tables &

   

3. 然后用空密码方式使用root用户登录 MySQL； 

   mysql -u root

   

4. 修改root用户的密码； 

   ```
   mysql> update mysql.user set password=PASSWORD('newpassword') where User='root';  
   mysql> flush privileges;  
   mysql> quit
   ```

5. 重新启动MySQL

    /etc/init.d/mysql restart

   

   就可以使用新密码 newpassword 登录了

   





 