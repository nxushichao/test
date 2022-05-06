package com.gkdz.server.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sh
 * @version 1.0
 * @date 2021/11/11 14:13
 * <p>对象存储初始化配置
 */
@Configuration
@Data
@Slf4j
@ConfigurationProperties(prefix = "my.minio")
public class MinioConfig {

  private String url;

  private String accessKey;

  private String secretKey;

  private String bucketName;

  @Bean
  public MinioClient getMinioClient() throws Exception {
    MinioClient minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    if (!found) {
      minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    } else {
      log.info("Bucket " + bucketName + " already exists.");
    }
    return minioClient;
  }
}
