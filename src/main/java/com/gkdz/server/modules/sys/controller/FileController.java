package com.gkdz.server.modules.sys.controller;

import com.gkdz.server.common.utils.R;
import com.gkdz.server.modules.sys.service.FileService;
import com.gkdz.server.modules.sys.vo.FileResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sh
 * @version 1.0
 * @date 2021/11/11 14:58
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传接口")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R<FileResponseVo> upload(MultipartFile multipartFile) {
        try {
            FileResponseVo fileResponseVo = fileService.upload(multipartFile);
            return new R<>(fileResponseVo);
        } catch (Exception e) {
            log.error("文件上传失败！fileName:{},异常原因:{}", multipartFile.getOriginalFilename(), e);
            return R.error("文件上传失败! 请稍后重试~");
        }
    }

    @ApiOperation("文件删除")
    @GetMapping("/delete/{fileName}")
    public R delete(@PathVariable("fileName") String fileName) {
        try {
            fileService.delete(fileName);
            return R.ok();
        } catch (Exception e) {
            log.error("文件删除失败,fileName:{}", fileName, e);
            return R.error("删除失败! 请稍后再试~");
        }
    }

    /**
     * 根据文件名获取静态url
     *
     * @param fileName 文件名
     * @return url
     */
    @ApiOperation("根据文件名获取静态url")
    @GetMapping("/getUrl/{fileName}")
    public R<String> getUrl(@PathVariable("fileName") String fileName) {
        try {
            String url = fileService.getUrl(fileName);
            return new R<>(url);
        } catch (Exception e) {
            log.error("根据文件名获取静态url,fileName:{}", fileName, e);
            return R.error("根据文件名获取静态url! 请稍后再试~");
        }
    }
}
