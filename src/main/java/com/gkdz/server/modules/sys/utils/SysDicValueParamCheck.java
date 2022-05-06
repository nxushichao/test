package com.gkdz.server.modules.sys.utils;

import com.gkdz.server.common.utils.Constant;
import com.gkdz.server.modules.sys.entity.SysDicValue;

import java.util.Objects;

/**
 * @author sh
 * @version 1.0
 * @date 2022/1/20 10:44
 */
public class SysDicValueParamCheck {
    /**
     * 检查新增时参数是否正确
     *
     * @param dicValue
     * @return
     */
    public static String checkAdd(SysDicValue dicValue) {
        if (Objects.isNull(dicValue)) {
            return "请求参数不可为空";
        }
        if (dicValue.getDicType() == null || dicValue.getDicType() <= 0) {
            return "字典类型错误";
        }
        if (dicValue.getId() != null) {
            return "新增时主键不可赋值";
        }
        return Constant.SUCCESS;
    }


    /**
     * 检查更新时参数是否正确
     *
     * @param dicValue
     * @return
     */
    public static String checkUpdate(SysDicValue dicValue) {
        if (Objects.isNull(dicValue)) {
            return "请求参数不可为空";
        }
        if (dicValue.getDicType() == null || dicValue.getDicType() <= 0) {
            return "字典类型错误";
        }
        if (dicValue.getId() == null || dicValue.getId() <= 0) {
            return "更新时主键不可为空";
        }
        return Constant.SUCCESS;
    }
}
