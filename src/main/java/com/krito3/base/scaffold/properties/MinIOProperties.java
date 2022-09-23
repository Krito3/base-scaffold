package com.krito3.base.scaffold.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName OssProperties
 * @Description TODO
 * @Author 杨建飞
 * @Date 2020/8/27 10:30 上午
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOProperties {
    private String endpoint;
    private String bucketName;
    private String publicBucketName;
    private String accessKey;
    private String secretKey;
}
