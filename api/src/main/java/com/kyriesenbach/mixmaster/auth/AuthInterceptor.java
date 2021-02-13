package com.kyriesenbach.mixmaster.auth;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor
{
    private final SpotifyApi spotifyApi;
    private final String COOKIE_NAME;
    
    @Autowired
    public AuthInterceptor(SpotifyApi spotifyApi, Environment env)
    {
        this.spotifyApi = spotifyApi;
        COOKIE_NAME = env.getProperty("mixmaster.spotify.cookieName");
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        if (request.getCookies() != null)
        {
            final Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> COOKIE_NAME.equals(c.getName()))
                .findAny();
            
            cookie.ifPresent(c -> spotifyApi.setAccessToken(c.getValue()));
        }
        return true;
    }
}
