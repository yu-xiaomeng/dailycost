package com.xiaomeng.dailycost.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomeng.dailycost.base.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.xiaomeng.dailycost.auth.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    protected ObjectMapper objectMapper;

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        try {
            String header = req.getHeader(HEADER_STRING);

            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                chain.doFilter(req, res);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (Exception e) {
//            System.out.println("已捕获异常 {}"+ e.getClass().getName());
            if (e instanceof CredentialsExpiredException) {
                res.setContentType("application/json;charset=UTF-8");
                res.setStatus(401);
                res.getWriter().write("{\"message\": \"token expired\"}");
//                objectMapper.writeValue(res.getWriter(), ReturnCode.RC_USERNAME_OR_PASSWORD_ERROR);
            }
            else {
                throw e;
            }
        }
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String tokenHeader = request.getHeader(HEADER_STRING);

        if (tokenHeader != null) {
            String token = tokenHeader.replace(TOKEN_PREFIX, "");
            DecodedJWT jwt = JWT.decode(token);
            if( jwt.getExpiresAt().before(new Date())) {
                throw new CredentialsExpiredException("token expired");
            }

            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            if (user != null) {
                // new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(user,null,  new ArrayList<>());
            }

            return null;
        }

        return null;
    }

}

