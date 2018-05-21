package com.shinelon.httpserver.enums;

/**
 * CodeEnum.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
public enum CodeEnum {
    /***
     * 成功
     */
    SUCCESS("0000", "操作成功"),
    /***
     * 失败
     */
    FAIL("0001", "操作失败"),
    /***
     * 参数错误
     */
    PARAMETER_REEOR("0002", "参数错误"),
    /***
     * 权限错误
     */
    AUTH_REEOR("0003", "权限错误"),
    /***
     * 系统异常
     */
    EXCEPTION("9999", "系统异常");
    /**
     * 消息
     */
    private String msg;
    /**
     * 编码
     */
    private String code;

    private CodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
