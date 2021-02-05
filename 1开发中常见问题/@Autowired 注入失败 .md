## 解决方案



  根据分析，需要检查可能导致Spring Boot依赖注入失败的因素。

**1、检查扫描路径。**

  扫描路径是由@ComponentScan来指定的，默认为标注类当前包及当前包的子包。



  **注意事项一**：很多人没有使用@ComponentScan，但是使用了@SpringBootApplication。@SpringBootApplication是通过内部封装@ComponentScan注解来实现实例扫描的，所以使用@SpringBootApplication也是一样的。

  **注意事项二**：也可以通过为@ComponentScan或@SpringBootApplication注解指定参数来修改扫描路劲，示例：

```java
// 如果使用@ComponentScan注解：
@ComponentScan(basePackages = "com.be.fallback")

// 如果使用@SpringBootApplication注解：
@SpringBootApplication(scanBasePackages = "com.be.fallback")
```

**2、检查实例注册。**

  检查想要使用 @Autowired注解自动注入依赖的类，是否标注了用来注册给Spring Boot的注解。这些注解包括`@Component`，`@Service，@Repository，@Controller等。`

​	**注意事项：**new 出来的类不受 spring管理，即该类中不能使用@Autowired 注解注入其他类，如下ImageHandle 使用了@Autowired 注入某个实例，那么该类也必须是使用spring的上述注解将该类归到spring 进行管理，并且该类在其他地方的使用必须是使用注解@Autowired进行注入IOrderImgService类；若在 TestController 中使用 ImageHandle 必须是使用依赖注入方式，不能使用new方式，否则 ImageHandle 类中的  IOrderImgService 类会报空指针异常；且必须 ImageHandle类也要归为 spring管理，加入注解@Component；是因为**在spring中，只有被spring管理的类，该类中才能使用 @Autowired注解注入其他类，否则注入的类就会报空指针异常；**

```java
@Component
public class ImageHandle {

    private final Log logger = LogFactory.getLog(ImageHandle.class);

    @Autowired
    private IOrderImgService orderImgService;

    public synchronized void jdcjszsqb(){
        orderImgService.localImgUpload(orderId);
    }
}


@Controller
public class TestController {

    @Autowired
    private ImageHandle ImageHandle;

    public synchronized void test(){
        orderImgService.localImgUpload(orderId);
    }
}
```



**3、其他问题。**

  如果上述步骤检查完成，服务启动又没有产生其他异常，这时候基本上已经排查代码的问题。这时候需要检查依赖、开发环境等是否有问题。检查依赖需要了解自己需要哪些依赖，看是否配置齐全；检查开发环境，可以通过将代码拷贝到其他机器上执行来判断。