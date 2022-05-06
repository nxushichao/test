package com.gkdz.server.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
    /**
     * 成功
     **/
    public static final String SUCCESS = "success";
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String CREATE_KEY_PASSWORD = "gk3820515";
    public static final String REDIS_GATEWAY_LOC = "_loc";
    public static final int NETTY_SERVER_PORT = 10022;

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ReportStatus {
        Over("已上报"), Ready("未上报");
        private String message;

        ReportStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum StrDateFormat {
        yyyyMMdd("yyyy-MM-dd"), yyyyMMdd_HHmmss("yyyy-MM-dd HH:mm:ss");
        private String str;

        StrDateFormat(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}
