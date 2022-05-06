package ${package.Service};

import com.github.pagehelper.PageInfo;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${package.Parent}.dto.${entity}PageDto;
import ${package.Parent}.dto.${entity}SaveDto;
import ${package.Parent}.vo.${entity}InfoVo;

/**
* ${table.comment!} 服务
*
<#if author != "">
* @author ${author}
</#if>
<#if date != "">
* @since ${date}
</#if>
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
    * 新增${table.comment!}
    *
    * @param ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto ${table.comment!}新增对象
    */
    void add(${entity}SaveDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto);

    /**
    * 修改${table.comment!}
    *
    * @param ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto ${table.comment!}修改对象
    */
    void edit(${entity}SaveDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto);

    /**
    * 删除${table.comment!}
    *
    * @param ids ID集合
    */
    void delete(Long[] ids);

    /**
    * 查看${table.comment!}
    *
    * @param id ID
    * @return ${table.comment!}对象
    */
    ${entity}InfoVo info(Long id);

    /**
    * 分页${table.comment!}
    *
    * @param ${entity?substring(0, 1)?lower_case}${entity?substring(1)}PageDto ${table.comment!}分页参数
    * @return 分页信息
    */
    PageInfo<${entity}> page(${entity}PageDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}PageDto);
}
</#if>

