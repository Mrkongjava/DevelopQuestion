### [java求指定范围和小数位的随机数](http://i2534.iteye.com/blog/650711)

项目用到了这个功能,查了下资料就写了个,传上来存档:

```java
import java.math.BigDecimal;  
  
public class RandomTest {  
  
    public static void main(String[] args) {  
        float Max = 100, Min = 1.0f;  
        for (int i = 0; i < 10; i++) {  
            BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);  
            //保留30位小数并四舍五入
            System.out.println(db.setScale(30, BigDecimal.ROUND_HALF_UP) .toString());            
        }  
    }  
}  
```



一次输出结果:

23.469297994652212224764298298396
47.088372886993283827905543148518
22.925769767959746303631618502550
80.308514252635418984027637634426
64.534755723478625100142380688339
60.465337277172388041890371823683
58.456141660760032152666099136695
59.452482477733951782283838838339
2.192510410136614407150545957847
11.154283422227885935740232525859

 