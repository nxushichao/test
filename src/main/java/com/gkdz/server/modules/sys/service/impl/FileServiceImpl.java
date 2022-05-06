package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.codec.Base64;
import com.gkdz.server.common.utils.RedisUtils;
import com.gkdz.server.modules.sys.service.FileService;
import com.gkdz.server.modules.sys.vo.FileResponseVo;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author sh
 * @version 1.0
 * @date 2021/11/11 15:01
 */
@Service
public class FileServiceImpl implements FileService {

    @Value(" ${my.minio.bucketName}")
    private String bucketName;

    @Value("${my.minio.redisKeypre}")
    private String redisKeypre;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public FileResponseVo upload(MultipartFile multipartFile) throws Exception {
        FileResponseVo fileResponseVo = new FileResponseVo();
        InputStream stream = multipartFile.getInputStream();
        String[] split = multipartFile.getOriginalFilename().split("\\.");

        String name = multipartFile.getOriginalFilename().split("\\.")[0] + "_" + RedisUtils.getSerialNumber(redisTemplate, redisKeypre) + "." + split[split.length - 1];
        String s = name.toUpperCase();
        PutObjectArgs arg = null;
        if (s.contains("JPG") || s.contains("PNG") || s.contains("GEPG") || s.contains("GIF") || s.contains("PSD") || s.contains("WMF") || s.contains("avif") || s.contains("APNG") || s.contains("BMP")) {
            arg = PutObjectArgs.builder().bucket(bucketName).object(name).stream(stream, stream.available(), -1).contentType("image/png").build();
        } else if (s.contains("PDF") || s.contains("pdf")) {
            arg = PutObjectArgs.builder().bucket(bucketName).object(name).stream(stream, stream.available(), -1).contentType("application/pdf").build();
        } else {
            arg = PutObjectArgs.builder().bucket(bucketName).object(name).stream(stream, stream.available(), -1).build();
        }

        minioClient.putObject(arg);
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(name).method(Method.GET).build();
        fileResponseVo.setUrl(minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs));
        fileResponseVo.setName(name);
        return fileResponseVo;
    }

    @Override
    public void delete(String fileName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    @Override
    public String getUrl(String name) throws Exception {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(name).method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
    }

    @Override
    public FileResponseVo uploadImg(String base64, String name) {
        FileResponseVo fileResponseVo = new FileResponseVo();
        try {
            byte[] file = Base64.decode(base64);
            InputStream input = new ByteArrayInputStream(file);
            PutObjectArgs arg = PutObjectArgs.builder().bucket(bucketName).object(name).stream(input, input.available(), -1).build();
            minioClient.putObject(arg);
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(name).method(Method.GET).build();
            fileResponseVo.setUrl(minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs));
            fileResponseVo.setName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileResponseVo;
    }

    @Override
    public FileResponseVo upload(InputStream stream, String name) throws Exception {
        FileResponseVo fileResponseVo = new FileResponseVo();
        PutObjectArgs arg = PutObjectArgs.builder().bucket(bucketName).object(name).stream(stream, stream.available(), -1).build();
        minioClient.putObject(arg);
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(name).method(Method.GET).build();
        fileResponseVo.setUrl(minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs));
        fileResponseVo.setName(name);
        return fileResponseVo;
    }
}
