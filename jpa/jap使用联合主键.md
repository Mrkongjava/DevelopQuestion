# 设置多个主键（复合主键）

## **第一种：@IdClass来设置多个主键**

1、先写一个包含复合主键的类

```
@Data
public class UserPK implements Serializable {
    private Integer id;
    private Integer userId;
}
```

2、在Entity类中，按照如下方式使用

```
@Data
@Entity
@Table(name = "xx")
@IdClass(UserPK.class) //复合组件的class对象
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class User{

    /** * @description 主键 */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /** * @description 主键 */
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;
}
```