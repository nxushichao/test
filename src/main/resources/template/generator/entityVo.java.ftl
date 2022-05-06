package ${package.Parent}.vo;

<#if swagger??>
import io.swagger.annotations.ApiModel;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
</#if>
import ${package.Entity}.${entity};

/**
* ${table.comment!}<#if fileNameSuffix?contains('List')>列表展示对象<#elseif fileNameSuffix?contains('Get')>详情展示对象<#else></#if>
*
<#if author != "">
* @author ${author}
</#if>
<#if date != "">
* @since ${date}
</#if>
*/
<#if entityLombokModel>
@EqualsAndHashCode(callSuper = true)
@Data
</#if>
<#if swagger??>
@ApiModel(value = "${entity} <#if fileNameSuffix?contains('List')>列表展示对象<#elseif fileNameSuffix?contains('Get')>详情展示对象<#else></#if>", description = "${table.comment!}<#if fileNameSuffix?contains('List')>列表展示对象<#elseif fileNameSuffix?contains('Get')>详情展示对象<#else></#if>")
</#if>
public class ${entity}${fileNameSuffix} extends ${entity} {

}

