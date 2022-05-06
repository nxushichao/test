package ${package.Controller};

import ${package.Entity}.${entity};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Service}.${table.serviceName};
import com.gkdz.server.common.utils.R;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ${package.Parent}.dto.${entity}PageDto;
import ${package.Parent}.dto.${entity}SaveDto;
import ${package.Parent}.vo.${entity}InfoVo;
import javax.annotation.Resource;
import com.gkdz.server.common.validator.ValidatorUtils;
import com.gkdz.server.common.annotation.SysLog;


/**
* ${table.comment!} Controller
*
<#if author != "">
    * @author ${author}
</#if>
<#if date != "">
    * @since ${date}
</#if>
*/
@Api(tags = "${table.comment!}管理")
@Slf4j
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Resource
    private ${table.serviceName} ${table.serviceName?substring(0, 1)?lower_case}${table.serviceName?substring(1)};

    /**
    * 列表
    */
    @PostMapping("/page")
    @ApiOperation("列表")
    public R<PageInfo<${entity}>> page(@RequestBody ${entity}PageDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}PageDto){
        PageInfo<${entity}> page = ${table.serviceName?substring(0, 1)?lower_case}${table.serviceName?substring(1)}.page(${entity?substring(0, 1)?lower_case}${entity?substring(1)}PageDto);
        return new R<>(page);
    }


    /**
    * 查看
    */
    @GetMapping("/info/{id}")
    @ApiOperation("查看")
    public R<${entity}InfoVo> info(@PathVariable Long id){
        ${entity}InfoVo ${entity?substring(0, 1)?lower_case}${entity?substring(1)}InfoVo = ${table.serviceName?substring(0, 1)?lower_case}${table.serviceName?substring(1)}.info(id);
        return new R<>(${entity?substring(0, 1)?lower_case}${entity?substring(1)}InfoVo);
    }

    /**
    * 保存
    */
    @PostMapping("/save")
    @ApiOperation("保存")
    @SysLog("新增${table.comment!}")
    public R save(@RequestBody ${entity}SaveDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto){
        ValidatorUtils.validateEntity(${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto);
        ${table.serviceName?substring(0, 1)?lower_case}${table.serviceName?substring(1)}.add(${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto);
        return R.ok();
    }

    /**
    * 修改
    */
    @PostMapping("/update")
    @ApiOperation("修改")
    @SysLog("修改${table.comment!}")
    public R update(@RequestBody ${entity}SaveDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto){
        ValidatorUtils.validateEntity(${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto);
        ${table.serviceName?substring(0, 1)?lower_case}${table.serviceName?substring(1)}.edit(${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto);
        return R.ok();
    }

    /**
    * 删除
    */
    @PostMapping("/delete")
    @ApiOperation("删除")
    @SysLog("删除${table.comment!}")
    public R delete(@RequestBody Long[] ids){
        ${table.serviceName?substring(0, 1)?lower_case}${table.serviceName?substring(1)}.delete(ids);
        return R.ok();
    }

}
    </#if>

