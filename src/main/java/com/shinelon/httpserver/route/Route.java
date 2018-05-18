package com.shinelon.httpserver.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import com.shinelon.httpserver.service.AdLogic;

/**
 * Route.java
 *
 * @author syq
 *
 *         2018年5月18日
 */

public class Route {

    private static final Logger logger = LoggerFactory.getLogger(Route.class);

    private static Map<String, AdLogic> routeMap = new HashMap<>(8);

    public static void initMap(Map<String, AdLogic> initMap) {
        routeMap = initMap;
    }

    public static AdLogic lookFor(String path) {
        AntPathMatcher apm = new AntPathMatcher();
        AdLogic ret = null;
        Optional<String> key = routeMap.keySet().stream().filter(e -> apm.match(e, path)).findFirst();
        ret = routeMap.get(key.orElse(null));
        if (ret == null) {
            logger.error("path is error!,path:{}", path);
            throw new RuntimeException("path is error");
        }
        return ret;
    }

}
