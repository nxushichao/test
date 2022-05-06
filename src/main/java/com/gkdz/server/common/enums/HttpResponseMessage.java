package com.gkdz.server.common.enums;

/**
 * @author sh
 * @version 1.0
 * @date 2022/1/19 16:04
 */
public enum HttpResponseMessage {

    /******************success*****************/
    Success(200, "成功"),


    /********************param error**********/
    Param_Is_Null(400, "数据不可为空"), Param_Is_Error(400, "参数错误"),


    /*****************response error*****************/
    Server_error(500, "服务器异常");

    private final int key;
    private final String value;

    HttpResponseMessage(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(int key) {
        for (HttpResponseMessage value : values()) {
            if (value.getKey() == key) {
                return value.getValue();
            }
        }
        return null;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
