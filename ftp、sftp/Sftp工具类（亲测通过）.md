Sftp 工具类

 

引入依赖：

```xml
<!-- sftp文件上传与下载 -->
<dependency>
    <groupId>com.jcraft</groupId>
    <artifactId>jsch</artifactId>
    <version>0.1.54</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>4.3.20.RELEASE</version>
</dependency>
```

 

工具类代码：

```java
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 * @author lhm
 * @date 2019/05/27
 */
@Slf4j
public class SftpUtils {

    public static void main(String[] args) {

    }

    /**
     * 上传文件
     *
     * @param map ftp连接参数
     * @param localpath 本地路径
     * @param fileName  文件名
     * @param ftppath   ftp路径
     * @return
     */
    public static boolean upload(Map<String, String> map, String localpath, String fileName, String ftppath) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        boolean flag = false;
        try {
            session = jsch.getSession(map.get("username"), map.get("hostname"), Integer.parseInt(map.get("port")));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(map.get("password"));
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.put(localpath + fileName, ftppath + fileName);
            log.info(sftpChannel.pwd());
            //Vector<ChannelSftp.LsEntry> files=sftpChannel.ls("/home/emstest/test");
            sftpChannel.exit();
            flag = true;
        } catch (Exception e) {
            log.info(localpath + fileName+"上传文件报错："+ e.getMessage());
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param map ftp连接参数
     * @param localpath 本地文件路径
     * @param fileName  文件名
     * @param ftppath   ftp文件路径
     */
    public static boolean download(Map<String, String> map, String localpath, String fileName, String ftppath) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        boolean flag = false;
        try {
            session = jsch.getSession(map.get("username"), map.get("hostname"), Integer.parseInt(map.get("port")));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(map.get("password"));
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(ftppath + fileName, localpath + fileName);
            log.info(sftpChannel.pwd());
//            Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(ftppath);
            sftpChannel.exit();
            flag = true;
        } catch (Exception e) {
//            log.info("下载文件报错：{}", e);
//            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param map ftp连接参数
     * @param fileName  文件名
     * @param ftppath   ftp文件路径
     */
    public static boolean removeDir(Map<String, String> map, String ftppath, String fileName) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        boolean flag = false;
        try {
            session = jsch.getSession(map.get("username"), map.get("hostname"), Integer.parseInt(map.get("port")));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(map.get("password"));
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.rm(ftppath + fileName);
            log.info(sftpChannel.pwd());
//            Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(ftppath);
            sftpChannel.exit();
            flag = true;
        } catch (Exception e) {
            log.info("删除文件报错：{}", e);
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return flag;
    }

//    /**
//     * 比对文件
//     *
//     * @param map
//     * @return
//     */
//    public static List<String> todoFileList(Map<String, String> map, String resultPath, String teplmPash) {
//        JSch jsch = new JSch();
//        Session session = null;
//        Channel channel = null;
//        List<String> listNeed = new ArrayList<>();
//        List<String> listHave = new ArrayList<>();
//        try {
//            session = jsch.getSession(map.get("username"), map.get("hostname"), Integer.parseInt(map.get("port")));
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.setPassword(map.get("password"));
//            session.connect();
//            channel = session.openChannel("sftp");
//            channel.connect();
//            ChannelSftp sftpChannel = (ChannelSftp) channel;
//            Vector<ChannelSftp.LsEntry> ResultList = sftpChannel.ls(teplmPash);
//            for (ChannelSftp.LsEntry infile : ResultList) {
//                listHave.add(infile.getFilename());
//            }
//            Vector<ChannelSftp.LsEntry> tepemList = sftpChannel.ls(resultPath);
//            for (ChannelSftp.LsEntry outfile : tepemList) {
//                if (!listHave.contains(outfile.getFilename())) {
//                    listNeed.add(outfile.getFilename());
//                }
//            }
//            sftpChannel.exit();
//        } catch (Exception e) {
//            log.info("对比文件报错：{}", e);
//            e.printStackTrace();
//        } finally {
//            if (channel != null) {
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//        }
//        return listNeed;
//    }
//
//
//    public static List<String> findFiles(ChannelSftp sftp, String path, String filter) {
//        List<String> list = new ArrayList<>();
//        try {
//            sftp.ls(path, (ChannelSftp.LsEntry entry) -> {
//                String filename = entry.getFilename();
//                Pattern pattern = Pattern.compile(filter);
//                Matcher matcher = pattern.matcher(filename);
//                boolean isMatch = matcher.find();
//                if (entry.getAttrs().isReg() && isMatch) {
//                    list.add(matcher.group(1));
//                } else if (entry.getAttrs().isDir()) {
//                    if (!".".equals(filename) && !"..".equals(filename)) {
//                        list.addAll(findFiles(sftp, path + "/" + filename, filter));
//                    }
//                }
//                return ChannelSftp.LsEntrySelector.CONTINUE;
//            });
//        } catch (SftpException e) {
//            log.info("电动车获取推送失败文件报错：",e.getMessage());
//            log.error("电动获取取推送失败文件报错：",e);
//        }
//        return list;
//    }
//    /**
//     * 判断是否有重推的单
//     * 模糊搜索出当天的有error的单
//     * 在查出error单有没有ok文件，如果没有就重推，如果有就重推
//     */
//    public static List<String> pushAgainOrderList(Map<String, String> map,String errorPath,String okPath,String bakPath) {
//        JSch jsch = new JSch();
//        Session session = null;
//        Channel channel = null;
//        List<String> resultList = null;
//        try {
//            session = jsch.getSession(map.get("username"), map.get("hostname"), Integer.parseInt(map.get("port")));
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.setPassword(map.get("password"));
//            session.connect();
//            channel = session.openChannel("sftp");
//            channel.connect();
//            ChannelSftp sftpChannel = (ChannelSftp) channel;
//            String filter = "(datajson_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "\\d+)";
//            String errfilter = filter + "_error.txt";
//            String okfilter = filter + "_ok.txt";
//            String infilter = filter + "_in.txt";
//            List<String> errList = findFiles(sftpChannel, errorPath, errfilter);
//            List<String> okList = findFiles(sftpChannel, okPath, okfilter);
//            List<String> inList = findFiles(sftpChannel, okPath, infilter);
//            List<String> bakinList = findFiles(sftpChannel, bakPath, infilter);
//            //int i=0;
////            errList.forEach((x) -> log.info("错误文件：{}_error.txt", x));
////            okList.forEach((x) -> log.info("成功文件：{}_ok.txt", x));
//            resultList = errList.stream().filter(err -> !okList.contains(err)).collect(toList());  //交集
//            List<String>  resultList2 = bakinList.stream().filter(x -> !inList.contains(x)).collect(toList());  //交集
//            resultList.forEach((x) -> log.info("需要重推文件：{}.txt", x));
//            resultList.addAll(resultList2);
//            sftpChannel.exit();
//            return resultList;
//        } catch (JSchException e) {
//            log.info("电动车处理推送失败文件报错：",e.getMessage());
//            log.error("电动处理取推送失败文件报错：",e);
//        } finally {
//            if (channel != null) {
//                channel.disconnect();
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//        }
//        return null;
//    }
//
//    public static void pictostr() {
//        returnBitMap("http://pdfupload.oos.ctyunapi.cn/20190529148094107elecBicycle..jpg?AWSAccessKeyId=2569df4048deaaf622ec&Expires=2556028800&Signature=8rIfiqmMkC9lGRAHbR6RC9a6vOs%3D",
//                "D:\\", "test1.jpg");
//        FileInputStream fileInputStream = null;
//        File file = null;
//        try {
//            file = new File("D:\\test1.jpg");
//            fileInputStream = new FileInputStream("D:\\test1.jpg");
//            String str = Pic2Base64StrUtils.getImageStrByStream(fileInputStream);
//            System.out.println(str);
//        } catch (IOException e) {
//            log.info("下载申办图片报错：", e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fileInputStream != null) {
//                    fileInputStream.close();
//                }
//                if (file != null) {
//                    file.delete();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void returnBitMap(String urlAdd, String path, String fileName) {
//        URL url = null;
//        InputStream is = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            url = new URL(urlAdd);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
//            conn.setDoInput(true);
//            conn.connect();
//            is = conn.getInputStream();    //得到网络返回的输入流
//            byte[] data = new byte[10240];
//            int len = 0;
//            fileOutputStream = new FileOutputStream(path + fileName);
//            while ((len = is.read(data)) != -1) {
//                fileOutputStream.write(data, 0, len);
//                fileOutputStream.flush();
//            }
//        } catch (Exception e) {
//            log.info("下载图片报错：", e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (is != null) {
//                    is.close();
//                }
//                if (fileOutputStream != null) {
//                    fileOutputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
```

测试类：

```java
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @Auther: kxq
 * @Date: 2019/8/26 14:43
 * @Description: 定时器
 */
@Component
public class RequestTimer {

    Logger logger = LoggerFactory.getLogger(RequestTimer.class);

    @Value("${car.ftp.localPathIn}")
    private String localPathIn;//存放请求文件的文件夹
    @Value("${car.ftp.localPathOut}")
    private String localPathOut;//存放响应文件的文件夹

    /**
     * 定时获取in文件夹中的所有文件，遍历请求交管服务器，获得请求信息后，写入out文件夹内
     */
//    @Scheduled(cron = "${car.timer.requestTimer}")

//    @Scheduled(fixedRate = 2000)
    @Scheduled(fixedDelay = 5000)
    public void requestTimer() {

        logger.info("----------------------------定时器开始执行----------------------------------");
        String filepath = localPathIn;
        File file = new File(filepath);//File类型可以是文件也可以是文件夹
        File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中

        for (int i = 0; i < fileList.length; i++) {
            File file1 = fileList[i];
            String requestId = file1.getName();
            try {
                if (file1.isFile()) {//判断是否为文件
                    logger.info("处理第{}个文件，文件名:{}",i+1, requestId);
                    String xmlParam = readFile(file1);//读取文件内容

                    //处理请求的xml
                    String str = xmlParam.substring(xmlParam.indexOf(">") + 1, xmlParam.length());
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(new StringReader(str));
                    Element root = document.getRootElement();
                    Element write = root.element("writeObjectOut");//若存在该标签，表示为写入，反之为查询

                    String requestType = "queryObjectOut";
                    if (write != null) {
                        requestType = "writeObjectOut";
                    }

                    logger.info("CarController.getCarInfo 请求xml：{}", xmlParam);
//                    String requestResult = WebService.CommonInterface(requestType, SystemCategoryEnum.机动车登记业务.getValue(),
//                            "01C21", "", xmlParam);
                    String requestResult = "<?xml version=\"1.0\" encoding=\"GBK\"?\"><root><head><code>1</code><message>数据保存成功</message></head><body><vioSurveil id=\"1\"><jkfs>嘻嘻哈哈</jkfs></vioSurveil><vioSurveil id=\"2\"><jkfs>嘻嘻112哈哈</jkfs></vioSurveil></body></root>";
                    logger.info("CarController.getCarInfo 返回参数：{}", requestResult);

                    writeToText(localPathOut + requestId, requestResult);
                    Boolean deleteResult = file1.delete();
                    logger.info("文件删除结果：{}",deleteResult);
                }
            } catch (Exception e) {
                if (e instanceof DocumentException) {
                    logger.info("文件内容转xml失败，文件名为{}", requestId);
                } else {
                    logger.info("请求交管六合一系统，文件名为{}", requestId);
                }
                e.printStackTrace();
                continue;
            }
        }
        logger.info("----------------------------定时器结束执行----------------------------------");
    }

    /**
     * 读取文本文件内容
     * @param file
     * @return
     */
    public String readFile(File file) {
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            String line; // 用来保存每行读取的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                sb.append(line); // 将读到的内容添加到 buffer 中
                sb.append("\n"); // 添加换行符
                line = reader.readLine(); // 读取下一行
            }
            reader.close();
            is.close();
//            file.delete();
        } catch (Exception e) {
            logger.error("RequestTimer.readFile：文件内容读取失败");
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * json数据写到Txt文件
     * @param fileName 文件名
     * @param context   文件内容
     * @return 文件对象
     */
    public File writeToText(String fileName, String context) {
        String path = fileName;
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            // write
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(context);
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {
            file = null;
            logger.info("六合一系统FTP对接:【{}】，生成txt文件异常", fileName, e);
            logger.error("【{}】，生成txt文件异常", fileName, e);
        }
        return file;
    }
}
```

 

 

 

 

 

 

 