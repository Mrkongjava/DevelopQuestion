# Linux Crontab介绍

- Crontab 命令被用来提交和管理用户的需要周期性执行的任务

- Linux会自动启动Crontab进程，Crontab会每分钟定期检查是否有需要执行的任务，如果有，则自动执行；

  

- Linux系统中任务调度分为系统调度和用户调度

- 系统调度的配置文件：/etc/crontab

  - 系统调度只能被root用户使用，并且是全局的

- 用户调度的配置文件：/var/spool/cron/crontab

  - 用户级别的调度，不是全局的

​	

**手动控制Crontab进程的命令**

```shell
service crontab start
service crontab stop
service crontab restart
```



**修改用户级别的crontab文件**

crontab 命令，如 crontab -e 表示编辑 crontab 用户级别的配置文件

```shell
crontab  参数
-l	打印用户任务列表
-r	删除用户任务列表
-e	编辑用户任务列表
```



**示例：在用户级别的 crontab 配置中添加配置，实现定时往 一个文件中输出指定字符串**

```shell
# 第一步：打开crontab用户级别的配置文件，
crontab -e
# 然后添加下面的代码后保存；表示每分钟往 demo.log文件中定时输出 Helloworld 字符串
*/1 * * * * echo "Helloworld" >>/home/demo.log
```



**使用用户级别的 crontab 实现定时执行某个shell脚本**

- 定义shell脚本 demo.sh ，如下脚本是对mysql数据库进行热备份的脚本代码

  ```shell
  # 获取当前系统时间并格式化
  time=$(date "+%Y-%m-%d %H: %M: %S")
  echo "执行全量热备份 ${time}"
  
  # 全量热备份命令
  innobackupex --defaults-file=/etc/my.cnf --host=192.168.221.144 --user=admin --password=Abc_123456 --port=3306 --encrypt=AES256 --compress --compress-threads=10 --include=test.t_user --galera-info --encrypt-threads=10 --encrypt-key=123456111111111111111111 --encrypt-chunk-size 512 --no-timestamp --stream=xbstream -> /home/backup.xbstream
  ```

- 修改当前脚本的执行权限

  ```shell
  chmod -R 777 ./demo1.sh
  ```

- 在 用户级别的 crontab 配置文件中 定时调用上面定义的shell脚本

  ```shell
  # 每周一凌晨0点0分执行，demo1.sh文件，并将执行文件时的日志输出到demo1.log文件中
  0 0 * * 1  /home/shell/demo1.sh > /home/log/demo1.log 2>&1
  
  # 若想做简单测试，那么就可以将时间设置短一些，一分钟执行一次
  */1 * * * *  /home/shell/demo1.sh > /home/log/demo1.log 2>&1
  ```

  

## 语法

java的cron表达式可以精确到秒，而linux的cron表达式只能精确到分钟



语法1：取值范围

```shell
分钟：0~59
小时：0~23
日期：1~31
月份：1~12
星期：0~6，0表示星期天，1到6分别表示星期一到星期六
年份：可选列，写对应年份信息，如2020
```



语法2：符号的意义

```shell
# 表示从2018年到2020年，每个星期天、星期六，每隔12个小时执行一次的0分执行
# 从左到右，分别是 分钟、时、天、月份、星期、年份
0 */12 * * 0,6 2018-2020

*	  代表所有可能的值，即不限，如上，月和天都是*，表示每个月的每一天都是执行
，    定义枚举值，如上星期中，0,6 表示一个星期中只有星期六和星期天会执行
-     定义范围，如上年份中，表示2018年到2020年才会执行，其他年份不执行
/     定义频率，如上小时中，一天有24小时，表示间隔几个小时执行一次；若要执行哪些时间执行可以使用逗号，即枚举值方式实现
```



巩固示例1

```shell
0 6 * * *		每天早上6点执行
*/1 * * * *		每分钟执行
0 */2 * * *		每两个小时执行
0 0 * * * 		每天零点执行
0 0 1 * *  		每月1号零点执
3,15 8-11 * * * 每天上午8-11点的第3分钟和第15分钟执行
```



巩固示例2

```
10 1 * * 6,0			每周六、周日的1点10分执行
*/30 18-23 * * *		每天18-23点，每隔30分钟执行一次
0 */12 * * 0,6 2018-2020  		2018-2020 年之间，每周六、周日每隔12小时执行一次
```

