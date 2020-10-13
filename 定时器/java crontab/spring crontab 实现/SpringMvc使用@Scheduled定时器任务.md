# springmvc 定时器

applicationContext-jobs.xml 定时器配置，xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"  
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
  xmlns:task="http://www.springframework.org/schema/task"   
  xsi:schemaLocation="http://www.springframework.org/schema/beans 
  http://www.springframework.org/schema/beans/spring-beans.xsd     
   http://www.springframework.org/schema/task 
   http://www.springframework.org/schema/task/spring-task-3.2.xsd" 
   default-lazy-init="true">  

  <!-- 定时器配置 
  task:executor/@pool-size：可以指定执行线程池的初始大小、最大大小 
  task:executor/@queue-capacity：等待执行的任务队列的容量 
  task:executor/@rejection-policy：当等待队已满时的策略，分为丢弃、由任务执行器直接运行等方式 
  -->
  <task:scheduler id="scheduler" pool-size="10" />  
  <task:executor id="executor" keep-alive="3600" pool-size="100-200" 
  queue-capacity="500" rejection-policy="CALLER_RUNS" /> 
  <task:annotation-driven executor="executor" scheduler="scheduler" />
</beans>
```



Java 类 (注意：@Component 这个注解一定要加上，不然定时器不会执行)

```java
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job {

  //@Scheduled(fixedDelay = 5000)  5秒更新一次
  @Scheduled(cron = "0 */1 * * * ?")  //一分钟更新一次
  public void test(){
     System.out.println("我是定时器");
  }
}
```



 