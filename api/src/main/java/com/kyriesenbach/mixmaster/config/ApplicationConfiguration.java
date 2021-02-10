package com.kyriesenbach.mixmaster.config;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;

@Configuration
@Component
public class ApplicationConfiguration
{
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String REDIRECT_URI;
    
    @Autowired
    public ApplicationConfiguration(Environment env)
    {
        CLIENT_ID = env.getProperty("mixmaster.spotify.clientId");
        CLIENT_SECRET = env.getProperty("mixmaster.spotify.clientSecret");
        REDIRECT_URI = env.getProperty("mixmaster.spotify.redirectUri");
    }
    
    @Bean
    public SpotifyApi spotifyApi()
    {
        return new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRedirectUri(URI.create(REDIRECT_URI))
            .build();
    }
}
