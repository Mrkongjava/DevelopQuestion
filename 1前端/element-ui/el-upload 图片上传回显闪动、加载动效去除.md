使用 el-upload 的图片墙上传图片文件时，使用到自定义的 file-list 自定义文件列表，遇到两个问题

1. 图片上传回显时会出现闪动动效，体验不好
2. 图片从列表进到编辑页面时，回显图片列表数据，动效显示不好



解决方法：覆盖 el-upload 的上传动效

```css
<style>
  /*覆盖图片编辑回显的时的动效*/
  .el-upload-list__item {
    transition: none !important;
  }
</style>
```

