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

