package com.shinelon.httpserver.utils;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtUtil.java
 *
 * @author syq
 *
 *         2018年5月21日
 */
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String API_KEY = "myKey";

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final byte[] API_KEYS_ECRETBYTES = DatatypeConverter.parseBase64Binary(API_KEY);

    /***
     * 校验jwt是否合法
     * 
     * @param jwt
     * @return
     */
    public static boolean checkJwt(String jwt) {

        boolean ret = true;
        JwtParser parser = Jwts.parser().setSigningKey(API_KEYS_ECRETBYTES);
        try {
            parser.parseClaimsJws(jwt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ret = false;
        }
        return ret;
    }

    /***
     * 创建jwt
     *
     * @return
     */
    public static String createJwt() {
        Key signingKey = new SecretKeySpec(API_KEYS_ECRETBYTES, SIGNATURE_ALGORITHM.getJcaName());
        JwtBuilder builder = Jwts.builder().setSubject("可以根据特殊需求定制").signWith(SIGNATURE_ALGORITHM, signingKey);
        return builder.compact();
    }

    /***
     * 解析JWT
     *
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt) {
        JwtParser parser = Jwts.parser().setSigningKey(API_KEYS_ECRETBYTES);
        return parser.parseClaimsJwt(jwt).getBody();
    }
}
