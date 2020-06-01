# vue中使用el-dialog中ref获取不到

示例1：这样可以正常获取

```
<div class="zhy" ref="zhy">
</div>
....

<script>
  var zhyDiv= this.$refs.zhy
</script>
```

示例2：这样就获取失败

```
<el-dialog title="zhy" :visible.sync="zhyDialog" width="100%">
    <div class="zhy" ref="zhy">
    </div>
</el-dialog>
....

<script>
  var zhyDiv= this.$refs.zhy
</script>
```


分析无法获取的原因：可能是渲染顺序的问题



解决方案：

```
this.$nextTick(_ => {
   //获取上面示例2中的div标签
   var aaa = this.$refs.zhy)
   })
```


