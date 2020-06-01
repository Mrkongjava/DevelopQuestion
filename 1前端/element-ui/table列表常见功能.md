# 1、加序号

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



# 2、el-select 

## 	2.1 设置是否可清空

​		1、在 el-select 中添加属性 clearable  表示该下拉框选中的值可以清空，反之不能清空；若有该属性，不管是设置为false还是true，都是表示可以清空；

​		2、注意该属性只对单选下拉框生效；



# 3、el-upload

​	3.1 上传成功或失败等钩子带上自定义参数

​	作用：主要是用于解决多个 el-upload 组件在同一个页面进行上传操作时，使用自定义参数区分是哪个 el-upload 组件的上传成功回调，进而对对应 el-upload 所绑定的 file-list 对象进行操作

```
<el-upload
    :action="qiNiuUploadApi"
    list-type="picture-card"
    :on-preview="handlePictureCardPreview"
    :limit="5"
    :on-success="(response,file,fileList)=> imageUploadSuccess(response,file,fileList,name123,'name123')"
    :on-exceed="fileLimit"
    :file-list="name123"
    :headers="headers"
    :on-remove="(file,fileList)=> removeImage(file,fileList,name123,'name123')"
    >
    <i class="el-icon-plus"></i>
</el-upload>
<el-dialog :visible.sync="dialogVisible" :append-to-body='true'>
	<img width="100%" :src="dialogImageUrl"  alt="" >
</el-dialog>
<script>
  export default {
    data() {
      return {
        dialogImageUrl: '',
        dialogVisible: false,
        name123: [],
      	image123: [],
      headers: { 'Authorization': getToken()
      };
    },
    methods: {
        // 图片预览
        handlePictureCardPreview(file) {
          this.dialogVisible = true
          this.dialogImageUrl = file.url
        },
    // 图片上传成功事件
    imageUploadSuccess(response, file, fileList, images, type) {
      // images、type 都是自定义的参数
    },
    // 图片最大限制
    fileLimit(files, fileList) {
      this.$message.error('最多上传' + fileList.length + '个图片！')
    }
    }
  }
</script>
```

