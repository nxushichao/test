package com.gkdz.server.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author fengling
 * @since 2022-01-19
 */
@Data
@TableName("sys_dic_value")
@ApiModel(value = "DicValue对象", description = "字典")
public class SysDicValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字典key")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("字典名称")
    @TableField("dic_name")
    private String dicName;

    @ApiModelProperty("字典值")
    @TableField("dic_value")
    private String dicValue;

    @ApiModelProperty("字典类型")
    @TableField("dic_type")
    private Long dicType;

    @ApiModelProperty("父字典key")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("字典描述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("逻辑删除:0=未删除,1=已删除")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @ApiModelProperty("禁止删除:0=允许,1=禁止")
    @TableField("prohibit_delete")
    private Integer prohibitDelete;


}
