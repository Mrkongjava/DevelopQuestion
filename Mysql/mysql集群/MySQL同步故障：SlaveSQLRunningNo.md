# MySQL同步故障：Slave_SQL_Running:No

进入slave服务器，运行：

mysql> show slave status\G

​         .......
             Relay_Log_File: localhost-relay-bin.000535
              Relay_Log_Pos: 21795072
      Relay_Master_Log_File: localhost-bin.000094
           Slave_IO_Running: Yes
          Slave_SQL_Running: No
            Replicate_Do_DB: 
        Replicate_Ignore_DB: 
      ......

## 解决办法一

Slave_SQL_Running: No
1.程序可能在slave上进行了写操作

2.也可能是slave机器重起后，事务回滚造成的.
一般是事务回滚造成的：
解决办法：
mysql> stop slave ;
mysql> set GLOBAL SQL_SLAVE_SKIP_COUNTER=1;
mysql> start slave ;

 

## 解决办法二

首先停掉Slave服务：slave stop
到主服务器上查看主机状态：
记录File和Position对应的值

进入master

mysql> show master status;
+----------------------+----------+--------------+------------------+
| File                 | Position | Binlog_Do_DB | Binlog_Ignore_DB |
+----------------------+----------+--------------+------------------+
| localhost-bin.000094 | 33622483 |              |                  | 
+----------------------+----------+--------------+------------------+
1 row in set (0.00 sec)

 

 

mysql> show slave status\G
*************************** 1. row ***************************
........
            Master_Log_File: localhost-bin.000094
        Read_Master_Log_Pos: 33768775
             Relay_Log_File: localhost-relay-bin.000537
              Relay_Log_Pos: 1094034
      Relay_Master_Log_File: localhost-bin.000094
           Slave_IO_Running: Yes
          Slave_SQL_Running: Yes
            Replicate_Do_DB:

手动同步需要停止master的写操作！ 