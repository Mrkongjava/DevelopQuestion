# 1、获取当前系统的时间戳

```java
//方法 一  
System.currentTimeMillis();   
//方法 二  
Calendar.getInstance().getTimeInMillis();  
//方法 三  
new Date().getTime();  
```



# 2、判断当前时间是上午还是下午

```java
//结果为“0”是上午 结果为“1”是下午
GregorianCalendar ca = new GregorianCalendar();
System.out.println(ca.get(GregorianCalendar.AM_PM));
```



# 3、Calendar 常见用法

## 	3.1 获得当前年月日时分秒

```java
Calendar now = Calendar.getInstance();
System.out.println("年: " + now.get(Calendar.YEAR));
System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
System.out.println("分: " + now.get(Calendar.MINUTE));
System.out.println("秒: " + now.get(Calendar.SECOND));
```

## 	3.2 获得前一个月、前一天等时间

```java
Calendar cal = Calendar.getInstance();
cal.add(Calendar.YEAR,+1);//当前时间后一年，下面的都以此类推，+表示增减，-表示减少
cal.add(Calendar.MONTH, +1);
cal.add(Calendar.DAY_OF_MONTH,+1);
cal.add(Calendar.HOUR_OF_DAY,+1);
cal.add(Calendar.MINUTE,+1);
cal.add(Calendar.MILLISECOND,-15);

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println(sdf.format(cal.getTime()));
```

## 	3.3 获得当天0点和24点

```java
//获得当天0点时间（即今天的开始时间）
Calendar cal = Calendar.getInstance();
cal.set(Calendar.HOUR_OF_DAY, 0);
cal.set(Calendar.SECOND, 0);
cal.set(Calendar.MINUTE, 0);
cal.set(Calendar.MILLISECOND, 0);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println(sdf.format(cal.getTime()));

//获取今天的24点，即今天的结束时间，也是明天的开始时间
Calendar cal = Calendar.getInstance();
cal.set(Calendar.HOUR_OF_DAY, 24);
cal.set(Calendar.SECOND, 0);
cal.set(Calendar.MINUTE, 0);
cal.set(Calendar.MILLISECOND, 0);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println(sdf.format(cal.getTime()));

```

## 	3.4 获取本月第一天零点和最后一天24点时间

```
Calendar cal = Calendar.getInstance();
cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
//设置指定日期的第一天和最后一天

//getActualMinimum 方法是获取给定时间的最小值
//cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//因为每个月的第一天都是 01 因此也可以使用这种快捷方式设计
//cal.set(Calendar.DAY_OF_MONTH,01);

//getActualMaximum 方法是获取给定时间的最大值
cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println(sdf.format(cal.getTime()));
```

## 	3.5 判断两天时间是否为同一天

```
/**
 * 比较两个时间是否为同一天
 * @param date1
 * @param date2
 * @return  true，表示同一天；false表示不同一天
 */
public static boolean isSameDay(Date date1, Date date2) {  
    Calendar calDateA = Calendar.getInstance();  
    calDateA.setTime(date1);  
  
    Calendar calDateB = Calendar.getInstance();  
    calDateB.setTime(date2);  
  
    //分别比较两个时间的年月日，若都一样，则为同一天
    return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)  
            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)  
            && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB  
                    .get(Calendar.DAY_OF_MONTH);  
} 
```



# 4、格式化时间

```java
//1、将时间对象格式化
Date d = new Date();
System.out.println(d);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String dateNowStr = sdf.format(d);
System.out.println("格式化后的日期："+dateNowStr);
```



# 5、将时间格式的字符串转为时间对象

```
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

tring str = "2012-1-13 17:26:33";//要跟上面sdf定义的格式一样  
Date today = null;
try {
    today = sdf.parse(str);
} catch (ParseException e) {
    e.printStackTrace();
}
System.out.println("字符串转成日期："+today);
```



# 6、获取当前时间年月日，时分秒清零

```
//java.util.Calendar 的 Calendar.set(int field, int value) 方法来对指定的域清零
Date now = new Date();
Calendar cal = Calendar.getInstance();
cal.setTime(now);// 将时分秒,毫秒域清零
cal.set(Calendar.HOUR_OF_DAY, 0);
cal.set(Calendar.MINUTE, 0);
cal.set(Calendar.SECOND, 0);
cal.set(Calendar.MILLISECOND, 0);
System.out.printf("%1$tF %1$tT\n",cal.getTime());//cal.getTime()返回的Date已经是更新后的对象

//java.time.LocalDate 更简单
//java8提供了一个新的类LocalDate,是一个不包含时区,ISO-8601 格式的日期类(比如 “2016-12-28”),LocalDate对象是个只读取的(immutable class),java.util.Date可以转换为LocalDate。 
//java.sql.Date是java.util.Date的子类，是为了配合SQL DATE而设置的数据类型。java.sql.Date只包含年月日信息，时分秒毫秒都会清零。格式类似：YYYY-MM-DD。 
//在java8中LocalDate可以转换成java.sql.Date 
//这两者一结合，也就可以将Date转换为时分秒清零0的对象,而且代码更简洁，一行就能搞定。

Date now1 = new Date();// java.util.Date -> java.time.LocalDate
// java.time.LocalDate -> java.sql.Date
LocalDate localDate = now1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
Date newDate = java.sql.Date.valueOf(localDate);
System.out.printf("%1$tF %1$tT\n", newDate);
```



7、得到GMT 时间(格林时间)

```java
//GMT 时间(格林时间) 即中央时区时间，北京时间是在东八区，所以北京时间= 格林时间+8小时.java中得到GMT时间的大代码片段如下：
Calendar cd = Calendar.getInstance();
//参数1为时间格式，参数2是指定某个国家的时间展示样式，默认为当前国家
SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'",Locale.US);
sdf.setTimeZone(TimeZone.getTimeZone("GMT"));// 设置时区为GMT  
String str = sdf.format(cd.getTime());
System.out.println(str);
```

