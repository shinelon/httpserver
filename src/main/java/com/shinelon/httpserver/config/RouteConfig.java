package com.shinelon.httpserver.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shinelon.httpserver.route.Route;
import com.shinelon.httpserver.service.BizLogic;

/**
 * RouteConfig.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
@Configuration
public class RouteConfig {
    private static final Logger logger = LoggerFactory.getLogger(RouteConfig.class);
    @Autowired
    private ApplicationContext applicationContext;

    @Value("${netty.port}")
    private Integer port;
    @Value("${netty.context-path}")
    private String contextPath;
    @Value("${netty.auth-path}")
    private String auth;

    @Bean
    public Route initRoute() {
        Route route = new Route();
        Map<String, BizLogic> initMap = new HashMap<>(8);
        initMap.put("/login/**", applicationContext.getBean("loginAdLogicImpl", BizLogic.class));
        initMap.put("/banner/**", applicationContext.getBean("bannerAdLogicImpl", BizLogic.class));
        Route.setContextPath(contextPath);
        Route.setPathAuth(auth);
        Route.initMap(initMap);
        logger.debug("contextPath:{}", contextPath);
        logger.debug("authPath:{}", contextPath + auth);
        logger.debug("route:{}", initMap);
        return route;
    }

    @Bean
    public ServerProperty serverConfig() {
        return new ServerProperty(port);
    }
}
