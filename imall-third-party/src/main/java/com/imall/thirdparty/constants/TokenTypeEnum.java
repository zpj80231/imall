package com.imall.thirdparty.constants;

/**
 * token类型枚举
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
public enum TokenTypeEnum {

    Empty("", "获取token"),
    SERVER("SERVER", "Server  接口服务器"),
    CTI("CTI", "CTI  接口服务"),
    CDR("CDR", "CDR  话单与统计接口服务"),
    AGENT("AGENT", "Agent  坐席接口服务"),
    ZJH("ZJH", "中间号接口服务"),
    ;

    String type;
    String discription;

    TokenTypeEnum(String type, String discription) {
        this.type = type;
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
