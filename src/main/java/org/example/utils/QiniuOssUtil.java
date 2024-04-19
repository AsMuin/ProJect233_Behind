package org.example.utils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import java.io.InputStream;


public class QiniuOssUtil {
    public static String upload(InputStream inputStream, String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "h6e3bEICraF9Nja0McKaYJJ106IdwoCFhz8K0rZV";//七牛云的AK密钥
        String secretKey = "diY06VXVRkgGsqcYHYmxPQcymOeWTQT1l1f67x23";//七牛云的SK密钥
        String bucket = "asmuin-dev";//七牛云的空间名称
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "project/dev/"+fileName;
        String result = null;
        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
//                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
                if (response.statusCode == 200) {
                    System.out.println(response);
                    result = "http://si0bs20z6.hn-bkt.clouddn.com/" + key;//http://xxxxxxx.com/换成七牛云提供的测试域名或者空间绑定的域名
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return result;
    }
}
