package com.shinelon.httpserver.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.shinelon.httpserver.AdApplicationTest;

/**
 * AdLogicTest.java
 * 
 * @author syq
 *
 *         2018年5月18日
 */
public class AdLogicTest extends AdApplicationTest {

    @Autowired
    @Qualifier("bannerAdLogicImpl")
    private AdLogic banner;
    @Autowired
    @Qualifier("loginAdLogicImpl")
    private AdLogic login;

    @Test
    public void test() throws Exception {
        logger.info("{}", banner.execute(null));
        login.equals(null);
    }

}
