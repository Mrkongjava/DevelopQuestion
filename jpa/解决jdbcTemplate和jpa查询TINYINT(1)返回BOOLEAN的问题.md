### 解决jdbcTemplate和jpa查询TINYINT(1)返回BOOLEAN的问题



#### 问题背景

在项目中使用jpa和jdbcTemplate时，发现当对Tinyint类型的数据进行查询时，会被当作boolean类型返回。 
而在项目中，我们使用了大量的Tinyint来做枚举值，被当作布尔类型返回后，造成了程序的异常。

数据库字段设计：status TINYINT(1)  NOT NULL DEFAULT 0;



##### 分析

在mysql的官网 numeric-type-overview 这篇文档里面写到

BOOL, BOOLEAN

These types are synonyms for TINYINT(1). A value of zero is considered false. Nonzero values are considered true

也就是说，在mysql中对 TINYINT(1) 和 BOOLEAN 的处理是一样的。



##### 解决

查了不少文档，都是对 jpa 或者 jdbcTemplate的查询方法进行扩展，在返回的数据进行Mapper匹配时，判断如果是TINYINT类型，就作特殊处理。 
虽然这样能解决问题，但像这样普遍的问题，我相信mysql肯定有过考虑，然后在mysql的官方文档里面发现了这个配置

The data type returned for TINYINT(1) columns when tinyInt1isBit=true (the default) can be switched between Types.BOOLEAN and Types.BIT using the new configuration property transformedBitIsBoolean

也就是说，当你对mysql Connector设置了tinyInt1isBit=true后，它会将TINYINT(1)当作BIT也就是 
Types.BOOLEAN来处理。而且这个设置是默认开启的，即默认为true。因此设置为false即可；

于是在jdbc url后面添加了&tinyInt1isBit=false ，问题解决
