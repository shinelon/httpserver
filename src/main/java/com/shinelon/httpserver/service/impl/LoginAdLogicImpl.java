package com.shinelon.httpserver.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinelon.httpserver.service.AdLogic;

/**
 * LoginAdLogicImpl.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
@Service
public class LoginAdLogicImpl implements AdLogic {

    private static final Logger logger = LoggerFactory.getLogger(LoginAdLogicImpl.class);

    @Override
    public Object execute(Map<String, String> parms) throws Exception {
        logger.debug("LoginAdLogicImpl parms:{}", parms);
        return parms;
    }

}
