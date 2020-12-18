package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/spotify/auth")
public class AuthController
{
    private final SpotifyService spotifyService;
    
    @Autowired
    public AuthController(SpotifyService spotifyService)
    {
        this.spotifyService = spotifyService;
    }
    
    @GetMapping
    public URI authorize()
    {
        return spotifyService.getAuthURL();
    }
    
    @GetMapping("/accesstoken")
    public String getAccessToken(@RequestParam String authorizationCode) throws SpotifyWebApiException, ParseException, IOException
    {
        return spotifyService.getAccessToken(authorizationCode);
    }
}
