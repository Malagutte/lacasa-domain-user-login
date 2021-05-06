package br.com.lacasaburger.login.helpers;

public class SecurityHelper {

    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_USER_ID = "sub";
    
    private SecurityHelper() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}