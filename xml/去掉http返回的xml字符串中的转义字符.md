//去掉字符串中的转义字符，result表示的是待转义的字符

  result= HtmlUtils.**htmlUnescape**(result); 

​    

  //去掉返回字符串中的\t、回车\n、换行符\r、制表符\t

  result= StringUtil.replace(result);