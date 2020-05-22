# Java HashMap按key排序和按value排序的两种简便方法


HashMap的储存是没有顺序的,而是按照key的HashCode实现.
key=手机品牌,value=价格,这里以这个例子实现按名称排序和按价格排序.

        Map phone=new HashMap();
        phone.put("Apple",7299);
        phone.put("SAMSUNG",6000);
        phone.put("Meizu",2698);
        phone.put("Xiaomi",2400);
        System.out.println(phone);
直接输出HashMap得到的是一个无序Map(不是Arraylist那种顺序型储存)

{Meizu=2698, Apple=7299, Xiaomi=2400, SAMSUNG=6000}




1.按key排序
对名称进行排序,首先要得到HashMap中键的集合(keySet),并转换为数组,这样才能用Arrays.sort()进行排序

        Set set=phone.keySet();
        Object[] arr=set.toArray();
        Arrays.sort(arr);
        for(Object key:arr){
            System.out.println(key);
        }
得到已经排好序的键,

Apple
Meizu
SAMSUNG
Xiaomi

最后利用HashMap.get(key)得到键对应的值即可

        for(Object key:arr){
            System.out.println(key+": "+phone.get(key));
        }

2.按value排序
对价格进行排序,首先需要得到HashMap中的包含映射关系的视图(entrySet),
如图:

将entrySet转换为List,然后重写比较器比较即可.这里可以使用List.sort(comparator),也可以使用Collections.sort(list,comparator)
转换为list

 List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(phone.entrySet()); //转换为list
1
使用list.sort()排序

       list.sort(new Comparator<Map.Entry<String, Integer>>() {
          @Override
          public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
              return o2.getValue().compareTo(o1.getValue());
          }
      });
使用Collections.sort()排序

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
           @Override
           public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
               return o2.getValue().compareTo(o1.getValue());
           }
       });
两种方式输出结果

 //for循环
         for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
        }      
 //for-each循环
           for (Map.Entry<String, Integer> mapping : list){
            System.out.println(mapping.getKey()+": "+mapping.getValue());
        }      

输出结果


完整代码如下:
import java.util.*;

//Author:Hibiki last modified in 2018.10.04
public class HashMapSort {

    public static void main(String[] args) {
        Map phone = new HashMap();
        phone.put("Apple", 7299);
        phone.put("SAMSUNG", 6000);
        phone.put("Meizu", 2698);
        phone.put("Xiaomi", 2400);
        //key-sort
        Set set = phone.keySet();
        Object[] arr = set.toArray();
        Arrays.sort(arr);
        for (Object key : arr) {
            System.out.println(key + ": " + phone.get(key));
        }
        System.out.println();
        //value-sort
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(phone.entrySet());
        //list.sort()
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        //collections.sort()
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        //for
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
        }
        System.out.println();
        //for-each
        for (Map.Entry<String, Integer> mapping : list) {
            System.out.println(mapping.getKey() + ": " + mapping.getValue());
        }
    }
}
