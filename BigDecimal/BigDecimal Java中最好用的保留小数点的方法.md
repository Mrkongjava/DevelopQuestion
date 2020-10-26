Java 中保留小数位的方法有四种，如下：

```java
import java.math.BigDecimal; 
import java.text.DecimalFormat; 
import java.text.NumberFormat; 

public class format { 
  double f = 231.52;//0.9,2 

  public void m1() { 
    BigDecimal bg = new BigDecimal(f); 
    double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
    System.out.println(f1); 
} 

  /** 
   * DecimalFormat转换最简便 
   */ 
  public void m2() { 
    DecimalFormat df = new DecimalFormat("#.00"); 
    System.out.println(df.format(f)); 
} 

  /** 
   * String.format打印最简便 
   */ 
  public void m3() { 
    System.out.println(String.format("%.2f", f)); 
} 

  public void m4() { 
    NumberFormat nf = NumberFormat.getNumberInstance(); 
    nf.setMaximumFractionDigits(2); 
    System.out.println(nf.format(f)); 
} 

  public static void main(String[] args) { 
    format f = new format(); 
    f.m1(); 
    f.m2(); 
    f.m3(); 
    f.m4(); 
  } 
}
```

但如上，对于整数和纯小数就m2,m3,m4这三个方法就有问题了，所以最好用的就是m1中用到的BigDecimal，介绍如下：

 

BigDecimal.setScale()方法用于格式化小数点
setScale(1)表示保留一位小数，默认用四舍五入方式 
setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3 
setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4 
setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4

setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍

setScaler(1,BigDecimal.ROUND_CEILING)接近正无穷大的舍入

setScaler(1,BigDecimal.ROUND_FLOOR)接近负无穷大的舍入，数字>0和ROUND_UP作用一样，数字<0和ROUND_DOWN作用一样

setScaler(1,BigDecimal.ROUND_HALF_EVEN)向最接近的数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。



注释：

1：scale指的是你小数点后的位数。比如123.456则score就是3.

score()就是BigDecimal类中的方法啊。
比如:BigDecimal b = new BigDecimal("123.456");

b.scale(),返回的就是3.

2：roundingMode是小数的保留模式。它们都是BigDecimal中的常量字段,有很多种。
比如：BigDecimal.ROUND_HALF_UP表示的就是4舍5入。
3：pubilc BigDecimal divide(BigDecimal divisor, int scale, int roundingMode)
的意思是说：我用一个BigDecimal对象除以divisor后的结果，并且要求这个结果保留有scale个小数位，roundingMode表示的就是保留模式是什么，是四舍五入啊还是其它的，你可以自己选！

4：对于一般add、subtract、multiply方法的小数位格式化如下：

BigDecimal mData = new BigDecimal("9.655").setScale(2, BigDecimal.ROUND_HALF_UP);
    System.out.println("mData=" + mData);

----结果：----- mData=9.66

 