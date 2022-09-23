package com.krito3.base.scaffold.common.utils;

import com.krito3.base.scaffold.common.exception.BusinessException;
import com.krito3.base.scaffold.properties.MinIOProperties;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MinIORepository
 * @Description TODO
 * @Author 杨建飞
 * @Date 2020/9/27 11:12 上午
 **/
@EnableConfigurationProperties(MinIOProperties.class)
//@Service
@Slf4j
public class MinIOUtil {

    @Resource
    private MinIOProperties minIOProperties;

    private MinioClient minioClient;

    /**
     * 初始化 MinIO 客户端
     */
    @PostConstruct
    private void init() {
        try {
            minioClient = MinioClient
                    .builder()
                    .endpoint(minIOProperties.getEndpoint()).credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIOProperties.getBucketName()).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIOProperties.getBucketName()).build());
            }

            //多个策略时 逗号也需替换掉
            String listBucketAfter = "\"s3:ListBucket\",";
            String listBucketBefore = ",\"s3:ListBucket\"";

            //修改桶策略 关闭桶的xml列表展示
            String bucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(minIOProperties.getBucketName()).build());
            if (bucketPolicy.contains("s3:ListBucket")) {
                bucketPolicy = bucketPolicy.replaceAll(listBucketAfter, "");
                bucketPolicy = bucketPolicy.replaceAll(listBucketBefore, "");
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minIOProperties.getBucketName()).config(bucketPolicy).build());
            }

            String publicBucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(minIOProperties.getPublicBucketName()).build());
            if (publicBucketPolicy.contains("s3:ListBucket")) {
                publicBucketPolicy = publicBucketPolicy.replaceAll(listBucketAfter, "");
                publicBucketPolicy = publicBucketPolicy.replaceAll(listBucketBefore, "");
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minIOProperties.getPublicBucketName()).config(publicBucketPolicy).build());
            }

        } catch (Exception e) {
            log.error("minio初始化失败:", e);
            throw new BusinessException("minio初始化失败");
        }
    }


    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/10
     * @Descripate 上传文件
     **/
    public void uploadMultipartFile(MultipartFile multipartFile, String path) {
        try {
            long objectSize = multipartFile.getSize();
            double pSize = Math.ceil((double) objectSize / 10000.0D);
            pSize = Math.ceil(pSize / 5242880.0D) * 5242880.0D;
            long partSize = (long) pSize;
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName())
                    .object(String.format("%s%s%s", path, "/", multipartFile.getOriginalFilename()))
                    .stream(multipartFile.getInputStream(), objectSize, partSize)
                    .contentType("application/octet-stream")
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            log.error("上传失败:", e);
            throw new BusinessException("文件上传失败");
        }
    }

    public void uploadMultipartFile(MultipartFile multipartFile, String path, String fileName, Integer num) {
        try {
            long objectSize = multipartFile.getSize();
            double pSize = Math.ceil((double) objectSize / 10000.0D);
            pSize = Math.ceil(pSize / 5242880.0D) * 5242880.0D;
            long partSize = (long) pSize;
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName())
                    .object(String.format("%s%s%s", path, "/", fileName))
                    .stream(multipartFile.getInputStream(), objectSize, partSize)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw);) {
                e.printStackTrace(pw);
            }
            String errorInfo = sw.toString();
            if (errorInfo.contains("reduce")) {
                uploadMultipartFile(multipartFile, path, fileName, num + 1);
            }
            if (num <= 3) {
                uploadMultipartFile(multipartFile, path, fileName, num + 1);
            }
            log.error("上传失败:", e);
            throw new BusinessException("上传失败");
        }
    }

    public void uploadObject(String minIOPath, String filePath, Integer num) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName()).object(minIOPath).filename(filePath).build());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw);) {
                e.printStackTrace(pw);
            }
            String errorInfo = sw.toString();
            if (errorInfo.contains("reduce"))
                uploadObject(minIOPath, filePath, num + 1);

            if (num <= 3)
                uploadObject(minIOPath, filePath, num + 1);
            log.error("上传失败:", e);
            throw new BusinessException("上传失败");
        }
    }

    public void uploadObject(String minIOPath, String filePath) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder()
//                    .contentType("image/png")
                    .bucket(minIOProperties.getBucketName()).object(minIOPath).filename(filePath).build());
        } catch (Exception e) {
            log.error("上传失败:", e);
            throw new BusinessException("上传失败");
        }
    }

    public void downloadObject(String minIOPath, String filePath) {
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName()).object(minIOPath).filename(filePath).build());
        } catch (Exception e) {
            log.error("上传失败:", e);
            throw new BusinessException("上传失败");
        }
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/10
     * @Descripate 以流形式下载单个文件
     **/
    public InputStream getObject(String objectName) {
        try {
            if (!judgeExist(objectName)) {
                throw new BusinessException("文件不存在");
            }
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName())
                    .object(objectName).build();
            InputStream inputStream = minioClient.getObject(getObjectArgs);
            if (inputStream == null)
                throw new BusinessException("文件不存在");
            return inputStream;
        } catch (Exception e) {
            log.error("获取文件失败:", e);
            throw new BusinessException("获取文件失败");
        }
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/10
     * @Descripate 下载对象指定区域的字节数组做为流。（断点下载）
     **/
    public InputStream getObject(String objectName, long offset, Long length) {
        try {
            if (!this.judgeExist(objectName)) {
                throw new BusinessException("文件不存在");
            }
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minIOProperties.getBucketName())
                            .object(objectName)
                            .offset(offset)
                            .length(length)
                            .build()
            );

        } catch (Exception e) {
            log.error("获取文件失败:", e);
            throw new BusinessException("获取文件失败");
        }
    }


    /**
     * @return
     * @Author YangXu
     * @Date 上传文件
     * @Descripate
     **/
    public void putObject(String objectName, InputStream stream) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName())
                    .object(objectName)
                    .contentType("application/octet-stream")
                    .stream(stream, -1, 10485760)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            log.error("上传失败:", e);
            throw new BusinessException("上传失败");
        }
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/10
     * @Descripate 生成下载链接
     **/
    public String presignedGetObject(String objectName, Integer expires) {
        try {
            if (!this.judgeExist(objectName)) {
                throw new BusinessException("文件不存在");
            }
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minIOProperties.getBucketName())
                            .object(objectName)
                            .expiry(expires, TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception e) {
            log.error("生成下载链接失败:", e);
            throw new BusinessException("生成下载链接失败");
        }
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/12
     * @Descripate 拷贝对象
     **/
    public String copyObject(String objectName, String destObjectName) {
        try {
            if (!this.judgeExist(objectName)) {
                throw new BusinessException("文件不存在");
            }
            destObjectName = this.judgeExistAndReturnName(destObjectName);
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(minIOProperties.getBucketName())
                            .object(destObjectName)
                            .source(
                                    CopySource.builder()
                                            .bucket(minIOProperties.getBucketName())
                                            .object(objectName)
                                            .build())
                            .build());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw);) {
                e.printStackTrace(pw);
            }
            String errorInfo = sw.toString();
            if (errorInfo.contains("reduce"))
                copyObject(objectName, destObjectName);
            log.error("拷贝对象:", e);
            throw new BusinessException("拷贝对象失败");
        }
        return destObjectName;
    }

    public String copyObjectNew(String objectName, String destObjectName, Integer num) {
        try {
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(minIOProperties.getBucketName())
                            .object(destObjectName)
                            .source(
                                    CopySource.builder()
                                            .bucket(minIOProperties.getBucketName())
                                            .object(objectName)
                                            .build())
                            .build());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw);) {
                e.printStackTrace(pw);
            }
            String errorInfo = sw.toString();

            if (errorInfo.contains("reduce"))
                return copyObjectNew(objectName, destObjectName, num + 1);

            if (num <= 3)
                return copyObjectNew(objectName, destObjectName, num + 1);

            log.error("拷贝对象:", e);
            throw new BusinessException("拷贝对象失败");
        }
        return destObjectName;
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/12
     * @Descripate 判断是否存在若存在生成新路径
     **/
    public String judgeExistAndReturnName(String objectName) {
        if (this.judgeExist(objectName)) {
            int i = objectName.lastIndexOf(".");
            int j = objectName.lastIndexOf("副本(");
            if (j < 0) {
                String name = String.format("%s%s%s", objectName.substring(0, i), "副本(1)", objectName.substring(i));
                return judgeExistAndReturnName(name);
            } else {
                String s = objectName.substring(j + 3, i - 1);
                String name = String.format("%s%s%s", objectName.substring(0, j + 3), Integer.parseInt(s) + 1, objectName.substring(i - 1));
                return judgeExistAndReturnName(name);
            }
        }
        return objectName;
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/12
     * @Descripate 删除文件
     **/
    public void removeObject(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minIOProperties.getBucketName()).object(objectName).build());
        } catch (Exception e) {
            log.error("删除文件失败:", e);
        }
    }


    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/12
     * @Descripate 判断是否存在
     **/
    public boolean judgeExist(String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(minIOProperties.getBucketName()).object(objectName).build());
        } catch (Exception e) {
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            String errorInfo = sw.toString();
            if (!errorInfo.contains("NoSuchKey"))
                return judgeExist(objectName);
            //对象不存在
            return false;
        }
        return true;
    }

    public boolean judgeExistNew(String objectName, Integer num) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(minIOProperties.getBucketName()).object(objectName).build());
        } catch (Exception e) {
            log.error("文件不存在：", e);
            StringWriter sw = new StringWriter();
            String errorInfo = sw.toString();
            if (!errorInfo.contains("NoSuchKey"))
                judgeExist(objectName);
            //对象不存在
            return false;
        }
        return true;
    }

    /**
     * Get information of an object.
     *
     * @param objectName
     * @return
     */
    public StatObjectResponse getObjectStat(String objectName) {
        try {
            return minioClient.statObject(StatObjectArgs.builder().bucket(minIOProperties.getBucketName()).object(objectName).build());
        } catch (Exception e) {
            return null;
        }
    }

    public StatObjectResponse getObjectStatNew(String objectName, Integer num) {
        try {
            return minioClient.statObject(StatObjectArgs.builder().bucket(minIOProperties.getBucketName()).object(objectName).build());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            String errorInfo = sw.toString();
            log.error("文件不存在：", e);
            if (errorInfo.contains("reduce"))
                return getObjectStatNew(objectName, num + 1);

            if (num <= 3)
                return getObjectStatNew(objectName, num + 1);
            //对象不存在
            return null;
        }
    }


    /**
     * @return
     * @Author YangXu
     * @Date 2020/10/20
     * @Descripate 生成上传链接
     **/
    public String presignedPutObject(String originalName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(minIOProperties.getBucketName())
                    .object(originalName)
                    .expiry(1, TimeUnit.DAYS)
                    .build());
        } catch (Exception e) {
            log.error("生成上传链接:", e);
            throw new BusinessException("生成上传链接失败");
        }
    }

    public void uploadMultipartFileInputStream(InputStream inputStream, String path, String fileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minIOProperties.getBucketName()).object(path + fileName)
                            .stream(inputStream, -1, 10485760)
                            .contentType("pdf")
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传附件至public桶
     *
     * @param objectName 附件对象
     * @param stream     文件流
     * @author dx
     * @date 2021/6/9 13:58
     */
    public void putPublicObject(String objectName, InputStream stream) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minIOProperties.getPublicBucketName())
                    .object(objectName)
                    .stream(stream, -1, 10485760)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            log.error("上传失败:", e);
            throw new BusinessException("上传失败");
        }
    }

    /**
     * 复制附件至public桶
     *
     * @param objectName     附件对象
     * @param destObjectName 目标附件对象
     * @return {@link String} 附件路径
     * @author dx
     * @date 2021/6/9 14:00
     */
    public String copyPublicObject(String objectName, String destObjectName) {
        try {
            if (!judgePublicExist(objectName)) {
                log.info("================ 对象:{} 不存在 =====================", objectName);

            }
            if (!judgePublicExist(destObjectName)) {
                minioClient.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(minIOProperties.getPublicBucketName())
                                .object(destObjectName)
                                .source(
                                        CopySource.builder()
                                                .bucket(minIOProperties.getPublicBucketName())
                                                .object(objectName)
                                                .build())
                                .build());
            }
        } catch (Exception e) {
            throw new BusinessException("拷贝对象失败:", e);
        }
        return destObjectName;
    }

    /**
     * 删除public桶内附件
     *
     * @param objectName 附件对象
     * @author dx
     * @date 2021/6/9 14:05
     */
    public void removePublicObject(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minIOProperties.getPublicBucketName())
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("删除文件失败:", e);

        }
    }

    /**
     * 判断public桶内附件是否存在
     *
     * @param objectName 附件对象
     * @return {@link boolean}
     * @author dx
     * @date 2021/6/9 14:05
     */
    public boolean judgePublicExist(String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(minIOProperties.getPublicBucketName()).object(objectName).build());
        } catch (Exception e) {
            log.error("文件不存在", e);
            //对象不存在
            throw new BusinessException("文件不存在");
        }
        return true;
    }
}
