package com.shinelon.httpserver.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import com.shinelon.httpserver.service.BizLogic;

/**
 * Route.java
 *
 * @author syq
 *
 *         2018年5月18日
 */

public class Route {

    private static final Logger logger = LoggerFactory.getLogger(Route.class);

    private static Map<String, BizLogic> routeMap = new HashMap<>(8);

    private static String contextPath;

    private static String pathAuth;

    public static String getPathAuth() {
        return pathAuth;
    }

    public static void initMap(Map<String, BizLogic> initMap) {
        if (StringUtils.isBlank(contextPath)) {
            routeMap = initMap;
        } else {
            initMap.forEach((k, v) -> {
                routeMap.put(contextPath + k, v);
            });
        }
    }

    public static BizLogic lookFor(String path) {
        AntPathMatcher apm = new AntPathMatcher();
        BizLogic ret = null;
        Optional<String> key = routeMap.keySet().stream().filter(e -> apm.match(e, path)).findFirst();
        ret = routeMap.get(key.orElse(null));
        if (ret == null) {
            logger.error("path is error!,path:{}", path);
            throw new RuntimeException("path is error");
        }
        return ret;
    }

    public static void setContextPath(String contextPath) {
        Route.contextPath = contextPath;
    }

    public static void setPathAuth(String pathAuth) {
        Route.pathAuth = contextPath + pathAuth;
    }

}
