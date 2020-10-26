# 访问https，抛出的异常

javax.net.ssl.SSLHandshakeException: Received fatal alert: handshake_failure



部分网友解释：是因为jdk中jce的安全机制导致报的错，按照大家的方式，要去oracle官网下载对应的jce包替换jdk中的jce包。

jce所在地址： %JAVA_HOME%\jre\lib\security里的local_policy.jar,US_export_policy.jar

JDK7 http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

JDK8 http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

但是以上并没有解决我的问题，后来我是这样做的，可供测试

在请求连接之前，加上

 

System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");

https.protocols可以设置一个，也可以设置多个，需要设置什么值，大家自己测试

所有类型：TLSv1.2,TLSv1.1,TLSv1.0,SSLv3,SSLv2Hello 

 