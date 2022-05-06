package com.gkdz.server.modules.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu
 * @date 2022/2/28 14:41
 */
@Data
@ApiModel("文件上传响应")
public class FileResponseVo {

    @ApiModelProperty("文件url路径")
    private String url;

    @ApiModelProperty("文件仓库中文件名")
    private String name;
}
