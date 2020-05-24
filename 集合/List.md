# 1、ArrayList遍历删除

```
import java.util.*;

public class test {
    public static void main(String[] args) {

        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> iter = list.iterator();
        while(iter.hasNext()){
            Integer integer = iter.next();
            if(integer.equals(1)){
                iter.remove();
            }
        }
        System.out.println(list);
    }
}

```



# 2、五种去重方式

```java
package com.perye.dokit;

import java.util.*;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
    
        List list = new ArrayList();
        list.add(26);
        list.add(39);
        list.add(5);
        list.add(40);
        list.add(39);
        System.out.println(list);

        //1:使用java8新特性stream进行List去重 
        List newList = (List) list.stream().distinct().collect(Collectors.toList());
        System.out.println("java8新特性stream去重:"+newList);
        list.add(39);

        //2:双重for循环去重（容易出现超出边界，因删除后实际索引是变小了，但第一个for的索引已经是定值了）
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if(i!=j&&list.get(i)==list.get(j)) {
                    list.remove(list.get(j));
                }
            }
        }
        System.out.println("双重for循环去重:"+list);
        list.add(39);

        //注意事项：在set集合中，基本数据类型是不会重复的，但若是对象，只要不是同一个对象就是不重复，因对象比较的是内存地址，因此若是对象比较，我们最好比较对象的id；在代码set1.add(integer)中添加进去的为对象的id即可，若是重复的id是不能添加进去的，返回false；也就不能添加到newList1集合中（推荐）
        //方法三:set集合判断去重,不打乱顺序 
        Set set1 = new HashSet();
        List newList1 = new ArrayList();
        for (Object integer : list) {
            if(set1.add(integer)) {
                //若返回true，表示添加到set集合成功，则可以添加到新的数组中
                newList1.add(integer);
            }
        }
        System.out.println("set集合判断去重:"+list);
        list.add(39);

        //方法四:遍历后判断赋给另一个list集合 
        List newList2 = new ArrayList();
        for (Object integer : list) {
            if(!newList2.contains(integer)){
                //contains方法是判断新的list集合中是否有该元素，若返回false，表示没有，则添加到新的集合中
                newList2.add(integer);
            }
        }
        System.out.println("赋值新list去重:"+newList2);
        list.add(39);

        //方法五:set和list转换去重 
        Set set2 = new HashSet();
        List newList3 = new ArrayList();
        set2.addAll(list);
        newList3.addAll(set2);
        System.out.println("set和list转换去重:" + newList3);

    }
}

```



# 3、从List集合中随机取出一个元素

```java
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);
list.add(3);

Random random = new Random();
int n = random.nextInt(list.size());
System.out.println(list.get(n));
```

