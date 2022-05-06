package com.gkdz.server.common.utils;

import com.gkdz.server.common.exception.RRException;
import com.gkdz.server.modules.sys.entity.BasePage;

/**
 * @author sh
 * @version 1.0
 * @date 2022/1/19 15:15
 */
public class ParamCheckCommonUtils {
    /**
     * 检查分页数据，如无，则赋默认值
     */
    public static void checkBasePage(BasePage basePage) {
        Long page = basePage.getPage();
        Long limit = basePage.getLimit();
        basePage.setPage((page == null || page <= 0) ? 1 : page);
        basePage.setLimit((limit == null || limit <= 0) ? 8 : limit);
    }


    /**
     * 检查单个入参是否为Long数值类型
     */
    public static Long checkOneParamIsNumberType(Object obj) throws RRException {
        if (obj instanceof String) {
            String str = (String) obj;
            return Long.parseLong(str);
        }
        if (obj instanceof Integer) {
            return (Long) obj;
        }

        return 0L;
    }


}
