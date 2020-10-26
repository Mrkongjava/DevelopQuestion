# java中BigDecimal取整方法

```java
BigDecimal bd = new BigDecimal("12.1");

long l  = bd.setScale( 0, BigDecimal.ROUND_UP ).longValue(); // 向上取整
long l  = bd.setScale( 0, BigDecimal.ROUND_DOWN ).longValue(); // 向下取整
```

对于正数而言，ROUND_UP  = ROUND_CEILING，ROUND_DOWN = ROUND_FLOOR


各个roundingMode详解如下：

ROUND_UP：非0时，舍弃小数后（整数部分）加1，比如12.49结果为13，-12.49结果为 -13

ROUND_DOWN：直接舍弃小数

ROUND_CEILING：若 BigDecimal 是正的，则做 ROUND_UP 操作；如果为负，则做 ROUND_DOWN 操作 （一句话：取附近较大的整数）

ROUND_FLOOR: 如果 BigDecimal 是正的，则做 ROUND_DOWN 操作；如果为负，则做 ROUND_UP 操作（一句话：取附近较小的整数）

ROUND_HALF_UP：四舍五入（取更近的整数）

ROUND_HALF_DOWN：跟ROUND_HALF_UP 差别仅在于0.5时会向下取整

ROUND_HALF_EVEN：取最近的偶数

ROUND_UNNECESSARY：不需要取整，如果存在小数位，就抛ArithmeticException 异常

 