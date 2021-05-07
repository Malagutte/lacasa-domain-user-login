package br.com.lacasaburger.login.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Strings;

/**
 * JwtHelper
 */
public class JwtHelper {

    private JwtHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String getValueFieldFromTokem(String token, String field) {
        return (String) getPayload(token).get(field);
    }

    private static Claims getPayload(String token) {
        token = token.contains("Bearer") ? token.split(" ")[1] : token;

        return Jwts.parser().setSigningKey(base64Url(SecurityHelper.JWT_SECRET)).parseClaimsJws(token).getBody();
    }

    protected static String base64Url(String s) {
        byte[] bytes = s.getBytes(Strings.UTF_8);
        return Encoders.BASE64URL.encode(bytes);
    }
}