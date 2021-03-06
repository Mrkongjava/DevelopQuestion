# Java内部类的使用小结



内部类是指在一个外部类的内部再定义一个类。类名不需要和文件夹相同。

内部类可以是静态static的，也可用public，default，protected和private修饰。（而外部顶级类即类名和文件名相同的只能使用public和default）。



注意：内部类是一个编译时的概念，一旦编译成功，就会成为完全不同的两类。对于一个名为outer的外部类和其内部定义的名为inner的内部类。编译完成后出现outer.class和outer$inner.class两类。所以内部类的成员变量/方法名可以和外部类的相同。

## 1. 成员内部类

​    成员内部类，就是作为外部类的成员，可以直接使用外部类的所有成员和方法，即使是private的。同时外部类要访问内部类的所有成员变量/方法，则需要通过内部类的对象来获取。

​    要注意的是，成员内部类不能含有static的变量和方法。***\*因为成员内部类需要先创建了外部类，才能创建它自己的\****，了解这一点，就可以明白更多事情，在此省略更多的细节了。

​    在成员内部类要引用外部类对象时，使用outer.this来表示外部类对象；

​    而需要创建内部类对象，可以使用outer.inner  obj = outerobj.new inner();

```java
public class Outer { 
    public static void main(String[] args) { 
        Outer outer = new Outer(); 
        Outer.Inner inner = outer.new Inner(); 
        inner.print("Outer.new"); 
 
        inner = outer.getInner(); 
        inner.print("Outer.get"); 
    } 
 
    // 个人推荐使用getxxx()来获取成员内部类，尤其是该内部类的构造函数无参数时 
    public Inner getInner() { 
        return new Inner(); 
    } 
 
    public class Inner { 
        public void print(String str) { 
            System.out.println(str); 
        } 
    } 
} 
```



## 2. 局部内部类

​    局部内部类，是指内部类定义在方法和作用域内。Thinking in Java给了这么两个例子：

定义在方法内：

```java
public class Parcel4 { 
    public Destination destination(String s) { 
        class PDestination implements Destination { 
            private String label; 
 
            private PDestination(String whereTo) { 
                label = whereTo; 
            } 
 
            public String readLabel() { 
                return label; 
            } 
        } 
        return new PDestination(s); 
    } 
 
    public static void main(String[] args) { 
        Parcel4 p = new Parcel4(); 
        Destination d = p.destination("Tasmania"); 
    } 
} 
 定义在作用域里：
public class Parcel5 { 
    private void internalTracking(boolean b) { 
        if (b) { 
            class TrackingSlip { 
                private String id; 
                TrackingSlip(String s) { 
                    id = s; 
                } 
                String getSlip() { 
                    return id; 
                } 
            } 
            TrackingSlip ts = new TrackingSlip("slip"); 
            String s = ts.getSlip(); 
        } 
    } 
 
    public void track() { 
        internalTracking(true); 
    } 
 
    public static void main(String[] args) { 
        Parcel5 p = new Parcel5(); 
        p.track(); 
    } 
} 
```

​    局部内部类也像别的类一样进行编译，但只是作用域不同而已，只在该方法或条件的作用域内才能使用，退出这些作用域后无法引用的。

## 3. 嵌套内部类

​    嵌套内部类，就是修饰为static的内部类。声明为static的内部类，不需要内部类对象和外部类对象之间的联系，就是说我们可以直接引用outer.inner，即不需要创建外部类，也不需要创建内部类。

​    嵌套类和普通的内部类还有一个区别：普通内部类不能有static数据和static属性，也不能包含嵌套类，但嵌套类可以。而嵌套类不能声明为private，一般声明为public，方便调用。



## 4. 匿名内部类

​    有时候我为了免去给内部类命名，便倾向于使用匿名内部类，因为它没有名字。例如：

```java
((Button) findViewById(R.id.start)).setOnClickListener(new Button.OnClickListener() { 
    @Override 
    public void onClick(View v) { 
        new Thread() { 
 
            @Override 
            public void run() { 
                // TODO Auto-generated method stub 
            } 
 
        }.start(); 
    } 
}); 
```

匿名内部类是不能加访问修饰符的。要注意的是，new 匿名类，这个类是要先定义的，看下面例子：

```java
public class Outer { 
    public static void main(String[] args) { 
        Outer outer = new Outer(); 
        Inner inner = outer.getInner("Inner", "gz"); 
        System.out.println(inner.getName()); 
    } 
 
    public Inner getInner(final String name, String city) { 
        return new Inner() { 
            private String nameStr = name; 
 
            public String getName() { 
                return nameStr; 
            } 
        }; 
    } 
} 
 
//注释后，编译时提示类Inner找不到 
/* interface Inner { 
    String getName(); 
} */ 
```

同时在这个例子，留意外部类的方法的形参，当所在的方法的形参需要被内部类里面使用时，该形参必须为final。这里可以看到形参name已经定义为final了，而形参city 没有被使用则不用定义为final。为什么要定义为final呢？在网上找到本人比较如同的解释：

 “这是一个编译器设计的问题，如果你了解java的编译原理的话很容易理解。  

首先，内部类被编译的时候会生成一个单独的内部类的.class文件，这个文件并不与外部类在同一class文件中。  

当外部类传的参数被内部类调用时，从java程序的角度来看是直接的调用例如：  

```java
public void dosome(final String a,final int b){  
 class Dosome{public void dosome(){System.out.println(a+b)}};  
 Dosome some=new Dosome();  
 some.dosome();  
}  
```

从代码来看好像是那个内部类直接调用的a参数和b参数，但是实际上不是，在java编译器编译以后实际的操作代码是  

```java
class Outer$Dosome{  
 public Dosome(final String a,final int b){  
 this.Dosome$a=a;  
 this.Dosome$b=b;  
}  

 public void dosome(){  
 	System.out.println(this.Dosomea+this.Dosomeb);  
}  
}}  
```

从以上代码看来，内部类并不是直接调用方法传进来的参数，而是内部类将传进来的参数通过自己的构造器备份到了自己的内部，自己内部的方法调用的实际是自己的属性而不是外部类方法的参数。  

这样理解就很容易得出为什么要用final了，因为两者从外表看起来是同一个东西，实际上却不是这样，如果内部类改掉了这些参数的值也不可能影响到原参数，然而这样却失去了参数的一致性，因为从编程人员的角度来看他们是同一个东西，如果编程人员在程序设计的时候在内部类中改掉参数的值，但是外部调用的时候又发现值其实没有被改掉，这就让人非常的难以理解和接受，为了避免这种尴尬的问题存在，所以编译器设计人员把内部类能够使用的参数设定为必须是final来规避这种莫名其妙错误的存在。”

(简单理解就是，拷贝引用，为了避免引用值发生改变，例如被外部类的方法修改等，而导致内部类得到的值不一致，于是用final来让该引用不可改变)

​    因为匿名内部类，没名字，是用默认的构造函数的，无参数的，那如果需要参数呢？则需要该类有带参数的构造函数：

```java
public class Outer { 
    public static void main(String[] args) { 
        Outer outer = new Outer(); 
        Inner inner = outer.getInner("Inner", "gz"); 
        System.out.println(inner.getName()); 
    } 
 
    public Inner getInner(final String name, String city) { 
        return new Inner(name, city) { 
            private String nameStr = name; 
 
            public String getName() { 
                return nameStr; 
            } 
        }; 
    } 
} 
 
abstract class Inner { 
    Inner(String name, String city) { 
        System.out.println(city); 
    } 
    abstract String getName(); 
} 
```

注意这里的形参city，由于它没有被匿名内部类直接使用，而是被抽象类Inner的构造函数所使用，所以不必定义为final。

 

​    而匿名内部类通过实例初始化，可以达到类似构造器的效果：

```java
public class Outer { 
    public static void main(String[] args) { 
        Outer outer = new Outer(); 
        Inner inner = outer.getInner("Inner", "gz"); 
        System.out.println(inner.getName()); 
        System.out.println(inner.getProvince()); 
    } 
 
    public Inner getInner(final String name, final String city) { 
        return new Inner() { 
            private String nameStr = name; 
            private String province; 
 
            // 实例初始化 
            { 
                if (city.equals("gz")) { 
                    province = "gd"; 
                }else { 
                    province = ""; 
                } 
            } 
 
            public String getName() { 
                return nameStr; 
            } 
 
            public String getProvince() { 
                return province; 
            } 
        }; 
    } 
} 
 
interface Inner { 
    String getName(); 
    String getProvince(); 
} 
```



##### 5.内部类的继承

​    内部类的继承，是指内部类被继承，普通类 extents 内部类。而这时候代码上要有点特别处理，具体看以下例子：

```java
public class InheritInner extends WithInner.Inner { 
 
    // InheritInner() 是不能通过编译的，一定要加上形参 
    InheritInner(WithInner wi) { 
        wi.super(); 
    } 
 
    public static void main(String[] args) { 
        WithInner wi = new WithInner(); 
        InheritInner obj = new InheritInner(wi); 
    } 
} 
 
class WithInner { 
    class Inner { 
 
    } 
} 
```

可以看到子类的构造函数里面要使用**父类的外部类对象.super()**;而这个对象需要从外面创建并传给形参。

 

至于内部类的重载，感觉Thinking in Java的例子很复杂，在平常应用中应该很少，因为有点难懂，不清晰。而内部类和闭包之间的事情，暂时放下，以后再看。

 