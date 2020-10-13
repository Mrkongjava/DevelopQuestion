1、设定 maven 打包项目名字和将本地 jar 包打包时候添加到生产环境的包中

```xml
<build>
    <!--指定打包后的项目名字-->
    <finalName>mmall</finalName>
    <plugins>
      <!-- geelynote maven的核心插件之-complier插件默认只支持编译Java 1.4，因此需要加上支持高版本jre的配置，在pom.xml里面加上 增加编译插件 -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>1.7</source>
          <target>1.7</target>
          <encoding>UTF-8</encoding>
          <!--使用maven打包的时候，将本地jar包一起打包到线上，此时就需要进行如下配置，并将本地的jar包放到如下目录中，若没有该配置，那么项目本地运行时没有问题，
          但是在线上运行的时候就会提示缺少某些本地jar包-->
          <compilerArguments>
            <extdirs>${project.basedir}/src/main/webapp/WEB-INF/lib</extdirs>
          </compilerArguments>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

