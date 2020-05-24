1������map���ϵ�

```java
Map<String, Integer> tempMap = new HashMap<String, Integer>();
        tempMap.put("a", 1);
        tempMap.put("b", 2);
        tempMap.put("c", 3);


        // JDK1.4��
        // ��������һ hashmap entrySet() ����
        System.out.println("����һ");
        Iterator it = tempMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key=" + key + " value=" + value);
        }

        // JDK1.5��,Ӧ��������For-Eachѭ��
        // �������������Ƽ���������������ʱ��
        System.out.println("������");
        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            System.out.println("key=" + key + " value=" + value);
        }

        // ���������� hashmap keySet() ����
        System.out.println("������");
        for (Iterator i = tempMap.keySet().iterator(); i.hasNext();) {
            Object key = i.next();
            System.out.println("key=" + key + " value=" + tempMap.get(key));
        }
        for (Iterator i = tempMap.values().iterator(); i.hasNext();) {
            Object key = i.next();
            System.out.println(key);// ѭ�����value
        }

        // ���������� treemap keySet()����
        System.out.println("������");
        for (Object key : tempMap.keySet()) {
            System.out.println("key=" + key + " value=" + tempMap.get(key));
        }
```

