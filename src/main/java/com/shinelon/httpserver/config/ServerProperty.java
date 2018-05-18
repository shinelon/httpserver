package com.shinelon.httpserver.config;

/**
 * ServerConfig.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
public class ServerProperty {

    private Integer port;

    public ServerProperty(Integer port) {
        super();
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

}
