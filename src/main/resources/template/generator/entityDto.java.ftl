package ${package.Parent}.dto;


<#if swagger??>
import io.swagger.annotations.ApiModel;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
</#if>
import ${package.Entity}.${entity};

/**
* ${table.comment!}<#if fileNameSuffix?contains('List')>列表查询对象<#elseif fileNameSuffix?contains('Get')>详情查询对象<#elseif fileNameSuffix?contains('Save')>保存对象<#elseif fileNameSuffix?contains('Update')>更新对象<#elseif fileNameSuffix?contains('Remove')>删除对象<#else></#if>
*
<#if author != "">
* @author ${author}
</#if>
<#if date != "">
* @since ${date}
</#if>
*/
<#if entityLombokModel>
@Data
@EqualsAndHashCode(callSuper = true)
</#if>
<#if swagger??>
@ApiModel(value = "${entity} <#if fileNameSuffix?contains('List')>列表查询对象<#elseif fileNameSuffix?contains('Get')>详情查询对象<#elseif fileNameSuffix?contains('Save')>保存对象<#elseif fileNameSuffix?contains('Update')>更新对象<#elseif fileNameSuffix?contains('Remove')>删除对象<#else></#if>", description = "${table.comment!}<#if fileNameSuffix?contains('List')>列表查询对象<#elseif fileNameSuffix?contains('Get')>详情查询对象<#elseif fileNameSuffix?contains('Save')>保存对象<#elseif fileNameSuffix?contains('Update')>更新对象<#elseif fileNameSuffix?contains('Remove')>删除对象<#else></#if>")
</#if>
public class ${entity}${fileNameSuffix} extends ${entity} {

}

