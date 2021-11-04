package com.xiaomeng.dailycost.auth;

public class SecurityConstants {
    public static final String SECRET = "My@Secret.key";
    public static final long EXPIRATION_TIME = 7200000; // 120 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user";
    public static final String LOGIN_URL = "/login";
}
