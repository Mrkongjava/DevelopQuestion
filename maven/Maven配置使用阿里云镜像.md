## Maven配置使用阿里云镜像（更新）

关于Maven配置使用阿里云镜像的方式如下：
只要在settings.xml文件中的mirrors下添加mirror标签即可

```xml
<!-- 阿里云仓库 -->
    <mirror>
        <id>alimaven</id>
        <mirrorOf>central</mirrorOf>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
    </mirror>
```

**这是以前的配置，在2020年，阿里已经更换了maven 配置指南，现在的配置如下**

```xml
<mirror>
  <id>aliyunmaven</id>
  <mirrorOf>*</mirrorOf>
  <name>阿里云公共仓库</name>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```



**当然，也可以选择点击下方链接配置:**
https://maven.aliyun.com/mvn/guide