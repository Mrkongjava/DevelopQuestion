## 设置mysql的表不区分你大小写

Linux上安装MySQL默认是数据库的表大小写敏感的。修改很简单，只要该一个mysql的配置文件就可以了。



1.用ROOT登录，修改/etc/my.cnf

2.在[mysqld]下加入一行：lower_case_table_names=1

[root@chicago init.d]# vi /etc/my.cnf
[mysqld]
lower_case_table_names=1



3.重新启动数据库即可

[root@chicago init.d]# service mysqld restart
Shutting down MySQL..                   [ OK ]
Starting MySQL......................................    [ OK ]

注意：这里service mysqld restart重启，而service mysqld start 表示启动



详解：

1、Linux下mysql安装完后是默认：区分表名的大小写，不区分列名的大小写；
2、用root帐号登录后，在/etc/my.cnf中的[mysqld]后添加添加lower_case_table_names=1，重启MYSQL服务，这时已设置成功：不区分表名的大小写；
lower_case_table_names参数详解：
lower_case_table_names=0
其中0：区分大小写，1：不区分大小写

MySQL在Linux下数据库名、表名、列名、别名大小写规则是这样的：
1、数据库名与表名是严格区分大小写的；
2、表的别名是严格区分大小写的；
3、列名与列的别名在所有的情况下均是忽略大小写的；
4、变量名也是严格区分大小写的；
MySQL在Windows下都不区分大小写。

**Linux系统中默认是mysql的数据库表名是区分大小写的**

**window系统中，mysql的数据库表名是不区分大小写的**

 

 