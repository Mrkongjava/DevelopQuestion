今天一大早，把我的数据都tar了一遍，结果。。。mysql数据库就出现了“Got error 28 from storage engine”错误

**XML/HTML代码**

1. 磁盘临时空间不够导致。 

2. 解决办法： 

3. 清空/tmp目录，或者修改my.cnf中的tmpdir参数，指向具有足够空间目录 

2、

**XML/HTML代码**

1. mysql报以下错的解决方法 

2. ERROR 1030 (HY000): Got error 28 from storage engine 
3. 出现此问题的原因：临时空间不够，无法执行此SQL语句 
4. 解决方法：将tmpdir指向一个硬盘空间很大的目录即可 

当然两个说法都是一样。所以基本肯定是空间没有了。去其他磁盘看了一下，果然。。
郁闷了一下，把备份文件先删除了就正常了。

 