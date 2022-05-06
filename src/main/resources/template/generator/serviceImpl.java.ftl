package ${package.ServiceImpl};

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import ${package.Parent}.dto.${entity}PageDto;
import ${package.Parent}.dto.${entity}SaveDto;
import ${package.Parent}.vo.${entity}InfoVo;
import com.gkdz.server.common.utils.MyPageHelper;

import java.util.List;


/**
* ${table.comment!} 服务实现
*
<#if author != "">
* @author ${author}
</#if>
<#if date != "">
* @since ${date}
</#if>
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public PageInfo<${entity}> page(${entity}PageDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}PageDto) {
        MyPageHelper.startPage(${entity?substring(0, 1)?lower_case}${entity?substring(1)}PageDto.getBasePage());
        List<${entity}> list = this.lambdaQuery().list();
        return new PageInfo<>(list);
    }


    @Override
    public void add(${entity}SaveDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto) {
        ${entity} ${entity?substring(0, 1)?lower_case}${entity?substring(1)} = BeanUtil.copyProperties(${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto, ${entity}.class);
        this.save(${entity?substring(0, 1)?lower_case}${entity?substring(1)});
    }

    @Override
    public void edit(${entity}SaveDto ${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto) {
        ${entity} ${entity?substring(0, 1)?lower_case}${entity?substring(1)} = BeanUtil.copyProperties(${entity?substring(0, 1)?lower_case}${entity?substring(1)}SaveDto, ${entity}.class);
        this.updateById(${entity?substring(0, 1)?lower_case}${entity?substring(1)});
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            this.removeById(id);
        }
    }

    @Override
    public ${entity}InfoVo info(Long id) {
        ${entity} ${entity?substring(0, 1)?lower_case}${entity?substring(1)}=this.getById(id);
        ${entity}InfoVo ${entity?substring(0, 1)?lower_case}${entity?substring(1)}InfoVo = BeanUtil.copyProperties(${entity?substring(0, 1)?lower_case}${entity?substring(1)}, ${entity}InfoVo.class);
        return ${entity?substring(0, 1)?lower_case}${entity?substring(1)}InfoVo;
    }

}
</#if>

