package com.shinelon.httpserver.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinelon.httpserver.service.AdLogic;

/**
 * BannerAdLogicImpl.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
@Service
public class BannerAdLogicImpl implements AdLogic {

    private static final Logger logger = LoggerFactory.getLogger(BannerAdLogicImpl.class);

    @Override
    public Object execute(Map<String, String> parms) throws Exception {
        logger.debug("BannerAdLogicImpl parms:{}", parms);
        return parms;
    }

}
