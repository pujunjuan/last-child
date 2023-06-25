package com.simple.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource(value = {"classpath:application.yml"})
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class OssConfig {
    //读取配置文件的内容

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String urlPrefix;
    @Bean
    public OSS oSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    @Bean
    @Scope("prototype")
    public OSS ossClient() {
                return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

}
