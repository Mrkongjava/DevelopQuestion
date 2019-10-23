# redis分布式锁的几种实现方式，以及Redisson的配置和使用

最近在开发中涉及到了多个客户端的对redis的某个key同时进行增删的问题。这里就会涉及一个问题：锁

# **先举例在分布式系统中不加锁会出现问题：**

　　redis中存放了某个用户的账户余额 ，例如100 （用户id：余额）

　　A端需要对用户扣费-1，需要两步：

　　　　A1.将该用户的目前余额取出来(100)

　　　　A2.将余额扣除一部分(99)后再插入到redis中

　　B端需要对用户充值+10，需要两步：

　　　　B1.将该用户的目前余额取出来(99)

　　　　B2.将余额添加充值额度(109)后再插入到redis中

　　我们的期望执行顺序是A1、A2、B1、B2  结果就会是109

　　但是如果不加锁，就会出现A1、B1、A2、B2(110)或者其他各种随机情况，这样就会造成数据错误。

# **Redis**加锁的几种实现方式

## 方式一，自己造轮子

　　之前参考的很多博客，关于redis加锁都是先setNX()获取锁，然后再setExpire()设置锁的有效时间。

　　然而这样的话获取锁的操作就不是原子性的了，如果setNX后系统宕机，就会造成锁死，系统阻塞。

　　根据官方的推荐（https://redis.io/topics/distlock），最好使用set命令：SET key value [EX seconds][PX milliseconds] [NX|XX]       

　　EX PX设置有效时间    NX属性的作用就是如果key存在就返回失败，否则插入数据。

　　**需要注意的是：**

　　在Redis 2.6.12之前，set只能返回OK,所以无法判断操作是否成功，所以也就不适用。

　    如果使用的是spring-boot-starter-data-redis依赖，那么在2.x版本之前的接口也不支持上述的set操作

　　　　java代码：

```
//获取锁
        //锁的键值需要具有标志性。
        //例如，现在有两个系统需要对key=user_id,value=user_balance进行操作，这时就可以设计这个键的锁为user_id+"_key"
        String user_id="1";
        String key=user_id+"_key";
        //值设置为一个随机数（下面讲原因）
        String random_value=UUID.randomUUID().toString();
        redisTemplate.execute((RedisCallback<Boolean>) (RedisConnection connection)->{
            //只有2.0以上的版本才支持set返回插入结果Boolean
            //此命令的意思是只有key不存在，才插入值，并且设置有效时间为10s
            connection.set(key.getBytes(), random_value.getBytes(), Expiration.seconds(10), SetOption.SET_IF_ABSENT);
            //本示例由于依赖版本低于2.0,所以无法接受set设置结果
            Boolean result=true;
            return result;
        });
        
        //进行更新操作...
        
        //释放锁
        //为什么释放之前要比较一下？
        //这是为了防止删除掉别人的锁，例如此场景中：如果我们的中间操作超过了10s那么锁会自动释放，这时别人会再获取锁。
        //如果我们执行完中间就直接删除锁的话，就会把别人的锁删除
        if(redisTemplate.opsForValue().get(key)==random_value) {
            redisTemplate.delete(key);
        }
```

可以发现，如果自己来实现的话，受限很多。并且这还是最基本的操作，包括出错重试等功能都没有。

所以我们要学习redis推荐的reids工具redisson

## 方式二：集成redisson

### 一.添加依赖

```
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>3.6.1</version>
</dependency>    
```

### 二.在resources文件夹添加配置文件redisson.yml

```
singleServerConfig:
  #连接空闲超时，单位：毫秒
  idleConnectionTimeout: 10000
  pingTimeout: 1000
  #连接超时，单位：毫秒
  connectTimeout: 10000
  #命令等待超时，单位：毫秒
  timeout: 3000
  #命令失败重试次数
  retryAttempts: 3
  #命令重试发送时间间隔，单位：毫秒
  retryInterval: 1500
  #重新连接时间间隔，单位：毫秒
  reconnectionTimeout: 3000
  #执行失败最大次数
  failedAttempts: 3
  #单个连接最大订阅数量
  subscriptionsPerConnection: 5
  #客户端名称
  clientName: null
  #地址
  address: "redis://192.168.1.16:6379"
  #数据库编号
  database: 0
  #密码
  password: xiaokong
  #发布和订阅连接的最小空闲连接数
  subscriptionConnectionMinimumIdleSize: 1 
  #发布和订阅连接池大小
  subscriptionConnectionPoolSize: 50
  #最小空闲连接数
  connectionMinimumIdleSize: 32
  #连接池大小
  connectionPoolSize: 64
  #是否启用DNS监测
  dnsMonitoring: false
  #DNS监测时间间隔，单位：毫秒
  dnsMonitoringInterval: 5000
threads: 0
nettyThreads: 0
codec: !<org.redisson.codec.JsonJacksonCodec> {}
transportMode : "NIO"
```

　　三.在Application中设置RedissonClient

```
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.xxx.mapper")
public class Application {
    public static void main(String [] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        RedissonClient redisson = Redisson.create(
                Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream()));
        return redisson;
    }
}
```

　　四.在代码中使用

```
    @Autowired
    private RedissonClient redisson;
    @Test 
    public void redisson() {
        String user_id="1";
        String key=user_id+"_key";
        //获取锁
        RLock lock = redisson.getLock(key);
        lock.lock();
            //执行具体逻辑...
        
        RBucket<Object> bucket = redisson.getBucket("a");
        bucket.set("bb");
        
        lock.unlock();
    }
```

需要注意的是redisson的使用和redisTemplate有比较大的区别，这里简单介绍一下几个特性：（刚用时迷了很久，希望大家能少走些弯路）

1.在redisson中不需要set指令，举个例子：

```
RBucket<Object> bucket = redisson.getBucket("a");
bucket.set("bb");
```

在这两条语句中，我们只获取了key="a"的bucket类型对象(里面可以装一个任意对象)。然后修改bucket里面一个值，其实这时["a","bb"]已经被存入redis了

2.所有的值都是结构体

和上例的RBucket结构体一样，redisson提供了十几种结构体（https://github.com/redisson/redisson/wiki/7.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%9B%86%E5%90%88）供我们使用，当取值时，redisson也会自动将值转换成对应的结构体。所以如果使用redisson取redisTemplate放入的值，就要小心报错

## 方式三.基于redlock的算法讨论

这种我还没有具体实现过，为什么会出现这种算法，主要是应对redis服务器宕机的问题。当redis宕机时，即使有主从，但是依然会有一个同步间隔。这样就会造成数据流失。

当然，更为严重的是，在分布式情况下，丢失的是锁，我们知道一般用锁的数据都是比较重要的。

一个场景：A在向主机1请求到锁成功后，主机1宕机了。现在从机1a变成了主机。但是数据没有同步，从机1a是没有A的锁的。那么B又可以获得一个锁。这样就会造成数据错误。

redlock主要思想就是做数据冗余。建立5台独立的集群，当我们发送一个数据的时候，要保证3台（n/2+1）以上的机器接受成功才算成功，否则重试或报错。

当然具体是很复杂，想研究的可以看看（https://redis.io/topics/distlock）

## 方式四.使用zookeeper+redis来管理锁

就像之前讨论的，方式2只能保证客户端的正确，却无法保证服务端的宕机数据丢失。方式三的数据完整性很高，但是管理起来很复杂。这时就有了一个折中的做法：

将锁存放在zookeeper中，由于zookeeper与redis的场景不同，所以zookeeper的算法对数据的完整性要求很高。在分布式的zookeeper中，数据是很难丢失的。

这样，我们就可以把锁放到zookeeper中，来保证锁的完整性。

好吧，这个我也没有实验过（羞耻），不过网上又很多这方面的博客，以后用到再说吧~~~一般项目用方式二就可以啦，

 