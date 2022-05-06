package ${package.Parent}.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.gkdz.server.modules.sys.entity.BasePage;


/**
* ${table.comment!}分页查询对象
*
* @author ${author}
* @since ${date}
*/
@Data
@ApiModel(value = "${table.comment!} 分页查询对象")
public class ${entity}${fileNameSuffix}  {

    @ApiModelProperty("分页参数")
    private BasePage basePage;


}

