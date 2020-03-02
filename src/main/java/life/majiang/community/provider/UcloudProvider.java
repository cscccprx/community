package life.majiang.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class UcloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String accessKey;
    @Value("${ucloud.ufile.private-key}")
    private String accessValue;
    @Value("${ucloud.endPoint}")
    private String endPoint;
    @Value("${ucloud.bucketName}")
    private String bucketName;

    public String upload(InputStream fileStream,String fileName){

        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1){
            generatedFileName = UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        }else
            throw new CustomizeException(CustomizeErrorCodeI.FILE_UPLOAD_FAIL);

        //上传到服务器
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, accessValue);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        //设置请求头
        objectMetadata.setContentType("image/jpg");
        ossClient.putObject(bucketName, generatedFileName, fileStream,objectMetadata);

        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        URL url = ossClient.generatePresignedUrl(bucketName, generatedFileName, expiration);
        if (url!=null)
            return url.toString();
        else
            throw new CustomizeException(CustomizeErrorCodeI.FILE_UPLOAD_FAIL);

    }

}
