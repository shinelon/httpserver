package com.shinelon.httpserver.utils;

import com.alibaba.fastjson.JSONObject;
import com.shinelon.httpserver.enums.CodeEnum;

/**
 * @author syq 统一返回json格式，主要用于接口使用
 * 
 */

public class JsonResultUtil {

    public static final String ERRORCODE = "code";
    public static final String ERRORMSG = "msg";
    public static final String DATA = "object";

    public static final String ERRORCODE_OK = CodeEnum.SUCCESS.getCode();
    public static final String ERRORCODE_OK_MSG = CodeEnum.SUCCESS.getMsg();
    public static final String ERRORCODE_ERROR = CodeEnum.FAIL.getCode();
    public static final String ERRORCODE_ERROR_MSG = CodeEnum.FAIL.getMsg();

    public static JSONObject getFailureJson() {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, ERRORCODE_ERROR);
        jsonObj.put(ERRORMSG, ERRORCODE_ERROR_MSG);
        jsonObj.put(DATA, "");
        return jsonObj;
    }

    public static JSONObject getFailureJson(Object data) {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, ERRORCODE_ERROR);
        jsonObj.put(ERRORMSG, ERRORCODE_ERROR_MSG);
        jsonObj.put(DATA, data);
        return jsonObj;
    }

    public static JSONObject getFailureJson(String msg, Object data) {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, ERRORCODE_ERROR);
        jsonObj.put(ERRORMSG, msg);
        jsonObj.put(DATA, data);
        return jsonObj;
    }

    public static JSONObject getJson(CodeEnum codeEnum) {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, codeEnum.getCode());
        jsonObj.put(ERRORMSG, codeEnum.getMsg());
        jsonObj.put(DATA, "");
        return jsonObj;
    }

    public static JSONObject getJson(CodeEnum codeEnum, Object data) {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, codeEnum.getCode());
        jsonObj.put(ERRORMSG, codeEnum.getMsg());
        jsonObj.put(DATA, data);
        return jsonObj;
    }

    public static JSONObject getJson(String code, String msg, Object data) {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, code);
        jsonObj.put(ERRORMSG, msg);
        jsonObj.put(DATA, data);
        return jsonObj;
    }

    public static JSONObject getSuccessJson() {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, ERRORCODE_OK);
        jsonObj.put(ERRORMSG, ERRORCODE_OK_MSG);
        jsonObj.put(DATA, "");
        return jsonObj;
    }

    public static JSONObject getSuccessJson(Object data) {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, ERRORCODE_OK);
        jsonObj.put(ERRORMSG, ERRORCODE_OK_MSG);
        jsonObj.put(DATA, data);
        return jsonObj;
    }

    public static JSONObject getSuccessJson(String msg, Object data) {
        JSONObject jsonObj = new JSONObject(true);
        jsonObj.put(ERRORCODE, ERRORCODE_OK);
        jsonObj.put(ERRORMSG, msg);
        jsonObj.put(DATA, data);
        return jsonObj;
    }
}
