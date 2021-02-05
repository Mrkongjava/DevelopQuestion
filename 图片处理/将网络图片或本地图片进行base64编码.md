```java
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * 图片转换base64码
 * @author Stefan
 */
public class ImageToBase64 {

    public static void main(String[] args) {
        try {
//            String str = getImgFromUrl("F:\\2.jpg");
            String str = getImgFromInternUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2400148759,4056346843&fm=26&gp=0.jpg");
            System.out.println(str);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据本地图片地址返回base64
     * @param imgUrl
     * @return
     * @throws Exception
     */
    public static String getImgFromLocalhostUrl(String imgUrl) throws Exception {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        File file = new File(imgUrl);

        try {
            is = new FileInputStream(file);
            outStream = new ByteArrayOutputStream();
            // 创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }

            // 对字节数组Base64编码
            return Base64Util.encode(outStream.toByteArray());

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }

            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

//    网络图片base64编码
    public static String getImgFromInternUrl(String imageUrl) throws Exception {

        ByteArrayOutputStream outputStream = null;

        try {
            URL url = new URL(imageUrl);
            BufferedImage bufferedImage = ImageIO.read(url);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"jpg",outputStream);
        } catch (IOException e) {
            throw new Exception(e);
        }

        BASE64Encoder encoder = new BASE64Encoder();
        String s= encoder.encode(outputStream.toByteArray());

        //s = s.replaceAll("\\r\\n","");
        //return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串

        return s;

    }
}
```

