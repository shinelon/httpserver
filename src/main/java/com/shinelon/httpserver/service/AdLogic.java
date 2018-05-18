package com.shinelon.httpserver.service;

import java.util.Map;

/**
 * adExecute.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
public interface AdLogic {
    /***
     * 执行业务逻辑
     * 
     * @param parms
     * @return
     * @throws Exception
     */
    Object execute(Map<String, String> parms) throws Exception;
}
