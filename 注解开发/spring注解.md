1、**@Column**注解：

-  @Column(name="(select title FROM yg_goods  WHERE id=goods_id)")

- 该注解可以用来映射实体类中的外键关联的实体类的属性，通过该外键查询关联实体类中的某个属性，将该属性查询到，然后映射到当前实体类中的字段中。

- 例如实体类如下：

  ```
  import javax.persistence.Column;
  
  public class YgGoodsIssue{
      private Integer id;
      private Integer goodsId;
  
      @Column(name="(select title FROM yg_goods  WHERE id=goods_id)")
      private String goodsTitle;
  
      //下面是关联的get、set方法。
  ```

- Sql语句原理如下：

  ```
  类似这个，就是将当前表和外键表关联起来，然后查询出来的外键关联的字段，放上别名，如下：
  SELECT
  	gi.id,
  	gi.goods_id goodsId,
  	( SELECT title FROM yg_goods WHERE id = goods_id ) goodsTitle 
  FROM
  	yg_goods_issue gi
  ```

  

- sdf 

