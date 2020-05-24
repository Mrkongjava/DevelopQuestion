1、遍历map集合的

```java
Map<String, Integer> tempMap = new HashMap<String, Integer>();
        tempMap.put("a", 1);
        tempMap.put("b", 2);
        tempMap.put("c", 3);


        // JDK1.4中
        // 遍历方法一 hashmap entrySet() 遍历
        System.out.println("方法一");
        Iterator it = tempMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key=" + key + " value=" + value);
        }

        // JDK1.5中,应用新特性For-Each循环
        // 遍历方法二（推荐，尤其是容量大时）
        System.out.println("方法二");
        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            System.out.println("key=" + key + " value=" + value);
        }

        // 遍历方法三 hashmap keySet() 遍历
        System.out.println("方法三");
        for (Iterator i = tempMap.keySet().iterator(); i.hasNext();) {
            Object key = i.next();
            System.out.println("key=" + key + " value=" + tempMap.get(key));
        }
        for (Iterator i = tempMap.values().iterator(); i.hasNext();) {
            Object key = i.next();
            System.out.println(key);// 循环输出value
        }

        // 遍历方法四 treemap keySet()遍历
        System.out.println("方法四");
        for (Object key : tempMap.keySet()) {
            System.out.println("key=" + key + " value=" + tempMap.get(key));
        }
```

