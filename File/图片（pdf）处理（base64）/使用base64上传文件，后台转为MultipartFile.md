# **使用base64上传文件，后台转为MultipartFile**

通常情况下，上传文件时，使用的都是file类型。我们再java后台应用只需要使用MultipartFile接收就可以了。有的时候，或许我们也会遇到使用base64进行文件上传。今天，我们一起学习下后台 应该如何处理这样的情况。

由于MultipartFile的实现类都不太适用于base64的上传文件。所以，我们需要自定义一个实现类：

```
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * 自定义的MultipartFile的实现类，主要用于base64上传文件，以下方法都可以根据实际项目自行实现
 */
public class BASE64DecodedMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;
    
    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
	}

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
	}

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);

    }
}
```

 

**同时，需要写一个base64转MultipartFile，如下：**

```
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import java.io.IOException;

@Slf4j

public class Base64UtilToMultiopartFile {
    //base64转图片    
    public static MultipartFile base64ImageToMultipart(String base64) {
        try {
            String[] baseStr = base64.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStr[1]);
            
            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStr[0]);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    //base64转pdf    
    public static MultipartFile base64PdfToMultipart(String base64) {
        byte[] bytes = Base64.decodeBase64(base64);
        return new BASE64DecodedMultipartFile(bytes, "data:pdf/pdf;base64,");
    }
}
```

接下来，就是上传处理了，废话不多说，代码如下：

```
/**
 * 上传图片
 */
@Slf4j
@RestController
public class CommentImageUploadController {

    @Value("${fileupload.path}")
    private String fileuploadPath;
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    
    public ApiResponse uploadImage(String image, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            MultipartFile multipartFile = Base64Util.base64ToMultipart(image);
            String originalFilename = multipartFile.getOriginalFilename();

            // 文件扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).trim();
            List<String> extList = Arrays.asList(".jpg", ".png", ".jpeg", ".gif");

            if (!extList.contains(ext)) {
                return ApiResponse.error("图片格式非法！");
            }
            String randomFilename = System.currentTimeMillis() + NumberUtil.getRandomString(6) + ext;

            //将文件写入服务器
            String fileLocalPath = fileuploadPath + "commentImage/" + randomFilename;
            File localFile = new File(fileLocalPath);
            multipartFile.transferTo(localFile);

            //写入服务器成功后组装返回的数据格式
            Map<String, Object> fileMap = new HashMap<>();
            
            //文件存放路径
            fileMap.put("filePath", "commentImage/" + randomFilename);
            
            return ApiResponse.success(fileMap);
        } catch (Exception e) {
            log.error("公众号评价商户上传图片失败：", e);
        }
        return ApiResponse.error();
    }
}
```

部分代码未做展示，但都是与该功能无关！

 