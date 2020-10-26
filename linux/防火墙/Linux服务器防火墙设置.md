1、Linux服务器的防火墙分为两种：

a) Iptables：centos6及centos6之前的都是使用iptables防火墙

 

b) Firewalld：centos7使用的是firewalld 防火墙

 

i. 开启端口：

查询端口号：firewall-cmd --query-port=8020/tcp

查询端口号8020 是否开启！

 

Ii：	开永久端口号：firewall-cmd --add-port=8020/tcp --permanent

这里把8020替换为需要开的端口号， --permanent是指永久的意思。

 

Iii：如何执行一行命令开多个端口号？

开永久端口号：firewall-cmd --add-port=8020/tcp --permanent&&开永久端口号：firewall-cmd --add-port=8088/tcp --permanent

 

开放端口后千万不要忘了重新启动防火墙！！！吃过亏的