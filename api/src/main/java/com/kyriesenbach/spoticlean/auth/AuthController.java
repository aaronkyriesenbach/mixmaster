package com.kyriesenbach.spoticlean.auth;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/spotify/auth")
public class AuthController
{
    private final AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }
    
    @GetMapping("/url")
    public URI getAuthUrl()
    {
        return authService.getAuthURL();
    }
    
    @GetMapping("/token")
    public String getAccessToken(@RequestParam String code, HttpServletResponse response) throws SpotifyWebApiException, ParseException, IOException
    {
        Cookie cookie = new Cookie("token", authService.getAccessToken(code));
        // Spotify access tokens expire after one hour
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return "Token served in cookie";
    }
}
