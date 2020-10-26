 

# ubuntu14.04下安装5.6版本的mysql

 

对于有些朋友，在Ubuntu14.04下安装MySQL5.6总是出错，这里我把我安装的过程给大家说一下： 


​	如果你登录的Ubuntu不是root账户，那么首先切换到root下，命令**sudo su root** 回车后会出现密码验证，这里是你普通用户登录的密码。直接验证就可以了。

 

一、在我们安装软件的时候，先更新一下Ubuntu的数据源：**sudo apt-get update**回车后它会自动更新数据源，等待结束后就可以下一步操作。

 

二、我们先安装MySQL需要的依赖 ：**apt-get install mysql-client-core-5.6和apt-get install mysql-client-5.6**

 

三、安装 MySQL：**apt-get install mysql-server-5.6**，安装过程中输入root的密码即可；

 

四、 验证原有主机是否已安装 
	这里主要是运行sudo netstat -tap | grep mysql命令查看是否有MySQL的端口，如果不加sudo的话因为权限无法顺利执行：

netstat -tap | grep mysql

如果安装成功，执行上面命令会显示：

tcp 0 0 localhost:mysql **:** LISTEN 6840/mysqld

 

四、键入命令来检查是否已启动MySQL：**netstat -tap | grep mysql** MySQL监听在localhost，说明MySQL已经启动。

 

五、使用命令行mysql -u root -p 来登录MySQL；因为这里安装的mysql默认是没有密码的，因此，输入上面的代码后直接回车就行。然后就会出现代码Enter password:  ，接着就直接回车就好，因为没有密码；这样就进入到了mysql了；

 

六、设置mysql的密码，在上一步进入mysql后，执行下面的代码 ：

mysql> set password for 'root'@'localhost' = password('zsyy!@#$')

表示设置当前系统下的mysql数据库的root用户的账号为zsyy!@#$

 

七、最后、你就可以操作你的数据库了。

 

八、若退出数据库后，重新登录mysql的root用户，就需要密码了，首先登录服务器，然后输入 mysql -u root -p 并回车，然后输入刚刚设置的密码即可。这里表示的是登录root用户。

 