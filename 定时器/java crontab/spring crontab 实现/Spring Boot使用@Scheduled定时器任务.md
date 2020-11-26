1. 假设已搭建好了一个基于Spring Boot项目，首先要在Application中设置启用定时任务功能 @EnableScheduling

   ```java
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.scheduling.annotation.EnableScheduling;
   
   @SpringBootApplication
   //启动定时任务(发现注解@Scheduled的任务并后台执行)
   @EnableScheduling
   public class Application {
     public static void main(String[] args) throws Exception {
       SpringApplication.run(Application.class);
     }
   }
   ```

   

2. 定时任务具体实现类（串行执行，即单线程执行所有定时任务，若有定时任务在执行，要等上一个定时任务执行完，才可以执行下一个定时任务，即使时间到了，即需要排队执行，默认串行执行）

    

   **注意： 需要在定时任务的类上加上注释：@Component，在具体的定时任务方法上加上注释@Scheduled即可启动该定时任务。**

   ```java
   import java.text.SimpleDateFormat;
   import java.util.Date;
   import org.springframework.scheduling.annotation.Scheduled;
   import org.springframework.stereotype.Component;
   
   @Component
   public class Scheduler{
     private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
   
     //每隔2秒执行一次
     @Scheduled(fixedRate = 2000)
     public void testTasks() {
       System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
     }
    
     //每天3：05执行
     @Scheduled(cron = "0 05 03 ? * *")
     public void testTasks() {
       System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
     }
   }
   ```

   

3. 并行执行（即多线程执行）：

   当定时任务很多的时候，为了提高任务执行效率，可以采用并行方式执行定时任务，任务之间互不影响，创建一个配置类，要实现SchedulingConfigurer接口就可以。如下：

   ```java
   @Configuration
   public class ScheduledConfig implements SchedulingConfigurer {
   
       @Override
       public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
         scheduledTaskRegistrar.setScheduler(setTaskExecutors());
       }
   
       @Bean(destroyMethod="shutdown")
       public Executor setTaskExecutors(){
         return Executors.newScheduledThreadPool(10); // 10个线程来处理。
       }
   }
   ```

   

4. @Scheduled参数描述

   ```java
   @Scheduled(fixedRate=3000)：上一次开始执行时间点后3秒再次执行；
   
   @Scheduled(fixedDelay=3000)：上一次执行完毕时间点3秒再次执行；
   
   @Scheduled(initialDelay=1000, fixedDelay=3000)：第一次延迟1秒执行，然后在上一次执行完毕时间点3秒再次执行；
   
   @Scheduled(cron="* * * * * ?")：按cron规则执行；
   ```

