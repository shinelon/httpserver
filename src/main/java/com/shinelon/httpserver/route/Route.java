package com.shinelon.httpserver.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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

    private static AntPathMatcher apm = new AntPathMatcher();

    private static LoadingCache<String, BizLogic> patchCache = CacheBuilder.newBuilder().concurrencyLevel(8)
            .expireAfterWrite(5, TimeUnit.MINUTES).initialCapacity(10).maximumSize(100)
            .build(new CacheLoader<String, BizLogic>() {
                @Override
                public BizLogic load(String key) throws Exception {
                    logger.debug("loading cache key:{}", key);
                    return lookFor(key);
                }
            });

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
        BizLogic ret = null;
        Optional<String> key = routeMap.keySet().stream().filter(e -> apm.match(e, path)).findFirst();
        ret = routeMap.get(key.orElse(null));
        if (ret == null) {
            logger.error("path is error!,path:{}", path);
            throw new RuntimeException("path is error");
        }
        return ret;
    }

    public static BizLogic lookForByCache(String path) {
        BizLogic ret = null;
        try {
            ret = patchCache.get(path);
        } catch (Exception e) {
            logger.warn("guava cache error ,continue with lookFor...");
            logger.warn(e.getMessage(), e);
            ret = lookFor(path);
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
