### 1、加序号

```
 <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55"/>
        
        <!--加序号-->
        <el-table-column
          label="序号"
          width="70px">
          <template slot-scope="scope">
            {{scope.$index+1}}
          </template>
        </el-table-column>
        
    </el-table>
```

