# Linux运行Jar的方法

1、前台运行

在linux服务器上运行Jar文件时通常的方法是：

java -jar test.jar

缺点：ssh窗口关闭时，程序中止运行

 

2、后台运行方式，&表示关闭窗口也不会中断

方法一： nohup java -jar test.jar &

nohup 意思是不挂断运行命令,当账户退出或终端关闭时,程序仍然运行

当用 nohup 命令执行作业时，缺省情况下该作业的所有输出被重定向到nohup.out的文件中，除非另外指定了输出文件。

 

方法二：$ nohup java -jar test.jar >temp.txt &

这种方法会把日志文件输入到你指定的文件中，没有则会自动创建



方法三：$ nohup java -jar test.jar >/dev/null 2>&1 &

/dev/null 2>&1 表示不创建nohup.log文件，即不在该文件中记录日志

 

jobs命令和 fg命令：

$ jobs

那么就会列出所有后台执行的作业，并且每个作业前面都有个编号。

*如果想将某个作业调回前台控制，只需要 fg + 编号即可。

$ fg 2

 

3、查看某端口占用的线程的pid

netstat -nlp |grep :8080

或 netstat -apn|grep 端口号

 

4、使用指定jdk运行

使用默认的jdk运行：

java -jar 项目文件名

nohup java -jar 项目文件名 &

使用指定jdk运行：

​     /usr/java/jdk1.8.0/bin/java -jar 项目名称 

nohup /usr/java/jdk1.8.0_181/bin/java -jar 项目名称 &