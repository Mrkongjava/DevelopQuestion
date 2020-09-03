# 1、设置当前 div 居中

方式1：使用  style="text-align:center;"  实现div居中

```
<div style="text-align:center;">
  <el-button
    v-permission="permission.add"
    class="filter-item"
    size="mini"
    type="primary"
    @click="addNation">保存</el-button>
  <el-button
    @click="nationCancel"
    size="mini"
    type="info"
    class="filter-item">取消</el-button>
</div>
```