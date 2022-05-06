package com.gkdz.server.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.exception.RRException;
import com.gkdz.server.modules.sys.entity.BasePage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author sh
 * @version 1.0
 * @date 2022/1/20 9:37
 */
public class MyPageHelper {
    public static void startPage(BasePage basePage) {
        String orderBy = "";
        String orderRule = Constant.ASC;
        ParamCheckCommonUtils.checkBasePage(basePage);
        if (StringUtils.isNotBlank(basePage.getOrderField())) {
            orderBy = "" + basePage.getOrderField();
            if (StringUtils.isNotBlank(basePage.getOrder())) {
                orderRule = basePage.getOrder().toLowerCase();
                if (Constant.ASC.equals(orderRule) || Constant.DESC.equals(orderRule)) {
                    orderBy = orderBy + " " + orderRule;
                } else {
                    throw new RRException("分页排序参数错误");
                }
            } else {
                orderBy = orderBy + " " + orderRule;
            }
        }
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.startPage(basePage.getPage().intValue(), basePage.getLimit().intValue(), orderBy);
        } else {
            PageHelper.startPage(basePage.getPage().intValue(), basePage.getLimit().intValue());
        }
    }

    /**
     * 将PageInfo对象泛型中的Po对象转化为Vo对象
     *
     * @param objP Po对象
     * @param <P>  Po类型
     * @param <V>  Vo类型
     * @return Vo的分页对象
     */
    public static <P, V> PageInfo<V> PageInfo2PageInfoVo(List<P> objP, List<V> objV) {
        PageInfo<P> pageInfoPo = new PageInfo<>(objP);
        Page<V> page = new Page<>(pageInfoPo.getPageNum(), pageInfoPo.getPageSize());
        page.setTotal(pageInfoPo.getTotal());
        PageInfo<V> vPageInfo = new PageInfo<>(page);
        vPageInfo.setList(objV);
        return vPageInfo;
    }
}
