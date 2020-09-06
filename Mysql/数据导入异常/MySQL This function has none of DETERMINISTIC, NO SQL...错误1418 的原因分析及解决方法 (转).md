## [MySQL This function has none of DETERMINISTIC, NO SQL...错误1418 的原因分析及解决方法 (转)](http://www.cnblogs.com/xihong2014/p/5566383.html)

**解决方法：**

解决办法也有两种， 第一种是在创建子程序(存储过程、函数、触发器)时，声明为DETERMINISTIC或NO SQL与READS SQL DATA中的一个， 例如: CREATE DEFINER = CURRENT_USER PROCEDURE `NewProc`()   DETERMINISTIC BEGIN #Routine body goes here... END;;

第二种是信任子程序的创建者，禁止创建、修改子程序时对SUPER权限的要求，设置log_bin_trust_routine_creators全局系统变量为1。

设置方法有三种:

1.在客户端上执行SET GLOBAL log_bin_trust_function_creators = 1; 也就是说在navicat上直接运行该sql语句。

2.MySQL启动时，加上--log-bin-trust-function-creators选贤，参数设置为1

3.在MySQL配置文件my.ini或my.cnf中的[mysqld]段上加log-bin-trust-function-creators=1

 