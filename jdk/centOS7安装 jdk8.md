1. 首先安装之前先检查一下系统有没有自带open-jdk

   命令：

   rpm -qa |grep java

   rpm -qa |grep jdk

   rpm -qa |grep gcj

   如果没有输出信息表示没有安装。

   

2. 

   如果遇到有可以使用命令批量卸载所有带有Java的文件

   rpm -qa | grep java | xargs rpm -e --nodeps 

   

3. 

   我们先检索包含java的列表

   yum list java*

   

4. 

   然后检索java1.8的列表

   yum list java-1.8*

   

5. 

   安装java1.8.0的所有文件

   yum install java-1.8.0-openjdk* -y

   如图安装完成

   

6. 

   使用命令检查是否安装成功

   查看版本号：java -version

   

7. 

   或者使用命令也可以

   javac

   