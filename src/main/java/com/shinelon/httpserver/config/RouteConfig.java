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
import com.shinelon.httpserver.service.AdLogic;

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

    @Bean
    public Route initRoute() {
        Route route = new Route();
        Map<String, AdLogic> initMap = new HashMap<>(8);
        initMap.put("/login/**", applicationContext.getBean("loginAdLogicImpl", AdLogic.class));
        initMap.put("/banner/**", applicationContext.getBean("bannerAdLogicImpl", AdLogic.class));
        Route.initMap(initMap);
        logger.debug("route:{}", initMap);
        return route;
    }

    @Bean
    public ServerProperty serverConfig() {
        return new ServerProperty(port);
    }
}
