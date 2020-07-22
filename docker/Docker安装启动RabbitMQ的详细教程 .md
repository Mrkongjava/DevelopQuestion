## Docker安装启动RabbitMQ的详细教程

 

1.下载，安装，和启动RabbitMQ

查找镜像

```
docker search rabbitmq
```

 

拉取镜像（**:management**表示有web管理界面）

```
docker pull rabbitmq:management
```

 

启动镜像

指定账号密码

```
docker run -d -p 15672:15672  -p  5672:5672  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin --name rabbitmq --hostname=主机名 镜像id
```

不指定账号密码

```
docker run -d -p 15672:15672  -p  5672:5672 --name rabbitmq --hostname=主机名 镜像id
```

 

参数解释：

15672 ：表示 RabbitMQ 控制台端口号，可以在[浏览器](https://www.2cto.com/os/liulanqi/)中通过控制台来执行 RabbitMQ 的相关操作。

5672 : 表示 RabbitMQ 所监听的 TCP 端口号，应用程序可通过该端口与 RabbitMQ 建立 TCP 连接，完成后续的异步消息通信

RABBITMQ_DEFAULT_USER：用于设置登陆控制台的用户名，这里我设置 admin

RABBITMQ_DEFAULT_PASS：用于设置登陆控制台的密码，这里我设置 admin

容器启动成功后，可以在浏览器输入地址：https://ip:15672/ 访问控制台

我的服务器是阿里云服务器，所以还需要去开放一下端口，否则是无法访问的。

这里有一个很重要的问题：RabbitMQ出于安全的考虑，默认是只能访问localhost:15762访问的，如果想用其他ip，是需要自己配置的。

 

```
docker exec -it rabbitmq /bin/bash
cd etc/rabbitmq/
vim rabbitmq.config
{rabbit,[{tcp_listeners,[5672]},{loopback_users,["admin"]}]}
rabbitmqctl add_user admin admin
rabbitmqctl set_permissions -p "/" admin "." "." ".*"
rabbitmqctl set_user_tags admin administrator
rabbitmqctl list_users
rabbitmqctl list_permissions -p /
```

