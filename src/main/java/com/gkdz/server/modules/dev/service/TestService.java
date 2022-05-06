package com.gkdz.server.modules.dev.service;

import com.github.pagehelper.PageInfo;
import com.gkdz.server.modules.dev.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.dev.dto.TestPageDto;
import com.gkdz.server.modules.dev.dto.TestSaveDto;
import com.gkdz.server.modules.dev.vo.TestInfoVo;

/**
*  服务
*
* @author 14651935
* @since 2022-05-05
*/
public interface TestService extends IService<Test> {

    /**
    * 新增
    *
    * @param testSaveDto 新增对象
    */
    void add(TestSaveDto testSaveDto);

    /**
    * 修改
    *
    * @param testSaveDto 修改对象
    */
    void edit(TestSaveDto testSaveDto);

    /**
    * 删除
    *
    * @param ids ID集合
    */
    void delete(Long[] ids);

    /**
    * 查看
    *
    * @param id ID
    * @return 对象
    */
    TestInfoVo info(Long id);

    /**
    * 分页
    *
    * @param testPageDto 分页参数
    * @return 分页信息
    */
    PageInfo<Test> page(TestPageDto testPageDto);
}

