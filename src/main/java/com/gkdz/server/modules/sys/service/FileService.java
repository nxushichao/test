package com.gkdz.server.modules.sys.service;

import com.gkdz.server.modules.sys.vo.FileResponseVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author sh
 * @version 1.0
 * @date 2021/11/11 14:59
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return 文件名+文件地址
     * @throws Exception 异常
     */
    FileResponseVo upload(MultipartFile multipartFile) throws Exception;

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    void delete(String fileName) throws Exception;


    /**
     * 根据文件名获取静态url
     *
     * @param name
     * @return
     * @throws Exception
     */
    String getUrl(String name) throws Exception;

    /**
     * 上传图片
     *
     * @param base64 图片的BASE63
     * @param name   名称
     * @return 地址
     */
    FileResponseVo uploadImg(String base64, String name);

    FileResponseVo upload(InputStream stream, String name) throws Exception;
}
