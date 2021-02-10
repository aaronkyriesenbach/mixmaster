package com.kyriesenbach.mixmaster.auth;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

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
        HashMap<String, String> cookies = new HashMap<>();
        if (request.getCookies() != null)
        {
            for (Cookie cookie : request.getCookies())
            {
                cookies.put(cookie.getName(), cookie.getValue());
            }
            
            if (cookies.get(COOKIE_NAME) != null)
            {
                spotifyApi.setAccessToken(cookies.get(COOKIE_NAME));
            }
        }
        return true;
    }
}
