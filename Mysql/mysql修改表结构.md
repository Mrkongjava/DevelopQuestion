# 用alter table语句修改表的结构

用alter table语句修改表的结构

1. 增加列

```sql
-- 列名后面是该列的对应信息 类型、是否为空、默认值 备注等
alter table 表名 add 列名 int not null DEFAULT 0 comment "年龄";

-- 示例，给 pet 表增加一列 weight
mysql>alter table pet add weight int not null DEFAULT 0 comment "年龄";
```

2. 删除列

   drop    英 [drɒp]   美 [drɑːp] v.  落下; 使掉下; 累倒; 停止; 把…除名; 急剧倾斜而下 ;n.  滴; 少量; 减少; 糖豆; 落差; 空投;

```sql
alter table 表名 drop 列名;

-- 示例：删除 pet 表中的 weight 这一列
mysql>alter table pet drop weight;
```

3. 改变列,
   1. 分为改变列的属性

```sql
-- 改变列属性，方法1(列名后面是该列的对应信息 类型、是否为空、默认值 备注等)
alter table 表名 modify 列名 int not null DEFAULT 0 comment "年龄";
-- 示例： 改变 pet 表 weight 的类型和其他属性
mysql>alter table pet modify weight varchar(30) not null DEFAULT "11" comment "年龄";

-- 改变列的属性, 方法2
alter table 表名 change 旧字段名 字段名 varchar(30) not null DEFAULT "11" comment "年龄";
-- 示例：改变weight的类型
alter table pet change weight weight varchar(30) not null DEFAULT "11" comment "年龄";
```

			2. 改变列的名字

```sql
alter table 表名 change 旧字段名 新字段名 相关对应属性

-- 示例，改变pet表中weight的名字
mysql>alter table pet change weight wei age1 int(10) DEFAULT 0 not null comment '我是年龄啊';


--注意事项：如果只是写修改完后的列名，不加列的默认值，不加列名说明，将会在修改的时候，就会修改失败，不指定新的列的其他信息，mysql默认不能让其修改成功，因为若修改成功则会将其他全部都覆盖掉了，所以这是不允许的；必须要执行修改该列名后还需要修改该列的那些信息；所以，在修改列名的时候，要保留列的基本信息，需要在修改列名的时候，强制写上去。
```

4. 改变表的名字

```sql
alter table tbl_name rename new_tbl

-- 示例：把pet表更名为animal
mysql>alter table pet rename animal;
```