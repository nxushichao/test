package com.gkdz.server.modules.dev.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.modules.dev.entity.Test;
import com.gkdz.server.modules.dev.mapper.TestMapper;
import com.gkdz.server.modules.dev.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.gkdz.server.modules.dev.dto.TestPageDto;
import com.gkdz.server.modules.dev.dto.TestSaveDto;
import com.gkdz.server.modules.dev.vo.TestInfoVo;
import com.gkdz.server.common.utils.MyPageHelper;

import java.util.List;


/**
*  服务实现
*
* @author 14651935
* @since 2022-05-05
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public PageInfo<Test> page(TestPageDto testPageDto) {
        MyPageHelper.startPage(testPageDto.getBasePage());
        List<Test> list = this.lambdaQuery().list();
        return new PageInfo<>(list);
    }


    @Override
    public void add(TestSaveDto testSaveDto) {
        Test test = BeanUtil.copyProperties(testSaveDto, Test.class);
        this.save(test);
    }

    @Override
    public void edit(TestSaveDto testSaveDto) {
        Test test = BeanUtil.copyProperties(testSaveDto, Test.class);
        this.updateById(test);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            this.removeById(id);
        }
    }

    @Override
    public TestInfoVo info(Long id) {
        Test test=this.getById(id);
        TestInfoVo testInfoVo = BeanUtil.copyProperties(test, TestInfoVo.class);
        return testInfoVo;
    }

}

