# Base64传输中加号会转变为空格

前一段时间做APP的拍照上传功能时，APP端传的图片的Base64字符串，后台无法转换为图片（图片显示黑底）。

最后发现，Base64中的得“+”全部显示空格（复制APP端传的字符串是断开的）。

解决方法（两种方式）：

1.方式一：后台代码 Base64str.replaceAll(" “,”+");

2.方式二：前端代码：encodeURIComponent(Base64str)；

3、方式三：

```
BASE64Decoder decoder = new BASE64Decoder();
byte[] bytes = decoder.decodeBuffer(imgData);

//调整异常数据
for (int i = 0; i < bytes.length; ++i) { 
    if (bytes[i] < 0) {
        bytes[i] += 256;
    }
}
```

 

在进行base64编码解码的时候出现的+号在通过html由后台php->get读取时会被自动替换成空格，造成乱码的问题

经过查证这并不是echo显示出错而是客观存在的

原因摘自：http://blog.csdn.net/wang0928007/article/details/7429568

“加号(+)是BASE64编码的一部分，而加号在QueryString中被当成是空格。    因此，当一个含有BASE64编码的字符串直接作为URL的一部分时，如果其中含有加号，则使用QueryString读取时，再使用BASE64解码就会发生错误。    解决的办法有两个：一是使用BASE64的字符串作为URL的一部分是，使用UrlEncode一类的函数进行编码；二是在接收BASE64字符串后，使用ReplaceAll将字符串中的空格替换成加号，然后再解码。  " 

 