package com.gkdz.server.common.validator;

import cn.hutool.core.util.StrUtil;
import com.gkdz.server.common.exception.RRException;

/**
 * 数据校验
 *
 * @author wu
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StrUtil.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
