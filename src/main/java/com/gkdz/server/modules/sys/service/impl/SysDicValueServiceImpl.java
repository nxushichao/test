package com.gkdz.server.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gkdz.server.modules.sys.dao.SysDicValueMapper;
import com.gkdz.server.modules.sys.entity.SysDicValue;
import com.gkdz.server.modules.sys.service.SysDicValueService;
import com.gkdz.server.modules.sys.vo.SysDicValueTreeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-01-19
 */
@Service
public class SysDicValueServiceImpl extends ServiceImpl<SysDicValueMapper, SysDicValue> implements SysDicValueService {

   /* @Override
    public List<DicValueTree> getByType(Long aLong) {
        List<DicValueTree> returnList = new LinkedList<>();
        List<DicValue> list = this.lambdaQuery().eq(DicValue::getDicType, aLong).list();
        if (!CollectionUtils.isEmpty(list)) {
            for (DicValue dicValue : list) {
                DicValueTree dicValueTree = new DicValueTree();
                BeanUtils.copyProperties(dicValue, dicValueTree);
                returnList.add(dicValueTree);
            }
            getTree(returnList);
        }
        return returnList;
    }

    public List<DicValueTree> getTree(List<DicValueTree> list){
        for (DicValueTree dic : list) {
            dic.setChild(selectTree(dic.getId(), list));
        }
        return list;
    }

    private List<DicValueTree> selectTree(Long id, List<DicValueTree> list) {
        List<DicValueTree> resultList = new ArrayList<>();
        // 迭代
        for (DicValueTree dic : list) {
            if (dic.getParentId().equals(id)) {
                dic.setChild(selectTree(dic.getId(), list));
                resultList.add(dic);
            }
        }
        return resultList;
    }
*/


    @Override
    public List<SysDicValueTreeVo> getByType(Long aLong) {
        List<SysDicValueTreeVo> returnList = new LinkedList<>();
        List<SysDicValue> list = this.lambdaQuery().eq(SysDicValue::getDicType, aLong).eq(SysDicValue::getParentId, 0L).list();
        for (SysDicValue dicValue : list) {
            SysDicValueTreeVo dicValueTree = new SysDicValueTreeVo();
            BeanUtils.copyProperties(dicValue, dicValueTree);
            List<SysDicValueTreeVo> treeList = queryChildTree(dicValue.getId());
            dicValueTree.setShowName(dicValueTree.getDicName());
            //评分模板展示时要拼接分值(也就是dicValue),很烦,1L就是评分模板的tpye
            if (StringUtils.isNotBlank(dicValue.getDicValue()) && dicValue.getDicType() == 1L) {
                dicValueTree.setShowName(dicValue.getDicName() + "(" + dicValue.getDicValue() + "分)");
            }
            if (!CollectionUtils.isEmpty(treeList)) {
                dicValueTree.setHasChild(true);
            } else {
                dicValueTree.setHasChild(false);
            }
            dicValueTree.setChild(treeList);
            returnList.add(dicValueTree);
        }

        return returnList;
    }


    @Override
    public SysDicValueTreeVo myGetById(Long id) {
        SysDicValue parent = this.getById(id);
        SysDicValueTreeVo dicValueTree = new SysDicValueTreeVo();
        BeanUtils.copyProperties(parent, dicValueTree);
        dicValueTree.setShowName(dicValueTree.getDicName());
        //评分模板展示时要拼接分值(也就是dicValue),很烦,1L就是评分模板的tpye
        if (StringUtils.isNotBlank(parent.getDicValue()) && parent.getDicType() == 1L) {
            dicValueTree.setShowName(parent.getDicName() + "(" + parent.getDicValue() + "分)");
        }
        List<SysDicValueTreeVo> treeList = queryChildTree(dicValueTree.getId());
        dicValueTree.setChild(treeList);
        if (!CollectionUtils.isEmpty(treeList)) {
            dicValueTree.setHasChild(true);
        } else {
            dicValueTree.setHasChild(false);
        }
        return dicValueTree;
    }

    private List<SysDicValueTreeVo> queryChildTree(Long id) {
        List<SysDicValueTreeVo> treeList = this.baseMapper.queryListByParentId(id);
        if (!CollectionUtils.isEmpty(treeList)) {
            for (SysDicValueTreeVo dicValueTree : treeList) {
                dicValueTree.setShowName(dicValueTree.getDicName());
                //评分模板展示时要拼接分值(也就是dicValue),很烦,1L就是评分模板的tpye
                if (StringUtils.isNotBlank(dicValueTree.getDicValue()) && dicValueTree.getDicType() == 1L) {
                    dicValueTree.setShowName(dicValueTree.getDicName() + "(" + dicValueTree.getDicValue() + "分)");
                }
                List<SysDicValueTreeVo> treeList1 = queryChildTree(dicValueTree.getId());
                dicValueTree.setChild(treeList1);
                if (!CollectionUtils.isEmpty(treeList1)) {
                    dicValueTree.setHasChild(true);
                } else {
                    dicValueTree.setHasChild(false);
                }
            }
        }
        return treeList;
    }
}