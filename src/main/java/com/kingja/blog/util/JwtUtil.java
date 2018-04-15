package com.kingja.blog.util;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Description:TODO
 * Create Time:2018/4/14 17:13
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    //该方法使用HS256算法和Secret:bankgl生成signKey
    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("KingJA");
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    //使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
    public static String encode(Map<String, Object> claims) {
        String compact = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
        return compact;
    }

    public static String createJWT(String id, String subject, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance());
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    //解析Token，同时也能验证Token，当验证失败返回null
    public static Claims decode(String jwt) {
        try {
            return Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            logger.error("Json web token verify failed");
            return null;
        }
    }

    public static void main(String[] args) {
        DataInfo kobe = new DataInfo(28, true, "Kobe");
        String jsonStr = new Gson().toJson(kobe);
        logger.error("jsonStr:" + jsonStr);
        String jwt = createJWT("23", jsonStr, 1000 * 60 * 60);
        logger.error("Jwt:" + jwt);
        Claims claims = decode(jwt);
        logger.error("Id:" + claims.getId());
        logger.error("Subject:" + claims.getSubject());
        logger.error("Expiration:" + DateUtil.getStringDate(claims.getExpiration()));

        DataInfo dataInfo = new Gson().fromJson(claims.getSubject(), DataInfo.class);

        logger.error("dataInfo:" +dataInfo.toString());

    }

   static class DataInfo {
        private int code;
        private boolean yes;
        private String msg;

        public DataInfo(int code, boolean yes, String msg) {
            this.code = code;
            this.yes = yes;
            this.msg = msg;
        }

       @Override
       public String toString() {
           return "DataInfo{" +
                   "code=" + code +
                   ", yes=" + yes +
                   ", msg='" + msg + '\'' +
                   '}';
       }
   }

}
