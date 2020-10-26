# BigDecimal解决商业运算中丢失精度的问题

 

BigDecimal在浮点型数据计算丢失精度的常见案例：

```java
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/20.
 */

public class BigDecimalTest {
  //和想象不太一样，很容易丢失精度
  //很多语言都有专门的货币计算，java没有
  //java在浮点型运算过程中会丢失精度
  //很可能造成一个用户有10元钱不能购买9.9999元的商品

  @Test
  public void test1(){
    System.out.println(0.05+0.01);
    System.out.println(1.0+0.42);
    System.out.println(4.015*100);
    System.out.println(123.3/100);

    /*
    0.060000000000000005
    1.42
    401.49999999999994
    1.2329999999999999
     */
  }

  //使用参数为double的构造方法
  @Test
  public void test2(){
    BigDecimal b1 = new BigDecimal(0.05);
    BigDecimal b2 = new BigDecimal(0.01);
    //b1.add(b2);
    System.out.println(b1.add(b2));
    //0.06000000000000000298372437868010820238851010799407958984375
    //又长又乱
  }

  //使用参数为String的构造方法
  @Test
  public void test3(){
    BigDecimal b1 = new BigDecimal("0.05");
    BigDecimal b2 = new BigDecimal("0.01");
    System.out.println(b1.add(b2));
    //0.06
    //用string 构造器，完美解决
  }
}
```



通过上面的测试可知，使用BigDecimal的string构造器，可以完美解决这个问题。

下面封装一个BigDecimalUntil工具类，方便以后使用：

```java
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/20.
 */
public class BigDecimalUntil {
  //使构造器不能在外部实例化
  private BigDecimalUntil(){
  }

  public static BigDecimal add(double v1,double v2){
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.add(b2);
  }

  public static BigDecimal sub(double v1,double v2){
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.subtract(b2);
  }

  public static BigDecimal mul(double v1,double v2){
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.multiply(b2);
  }

  public static BigDecimal div(double v1,double v2){
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
    //除不尽的时候要四舍五入，并且保留几位小数
  }
}
```



 