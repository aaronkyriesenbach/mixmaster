package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.URI;

@Configuration
public class ApplicationConfiguration
{
    @Autowired
    private Environment env;
    
    @Bean
    public SpotifyApi spotifyApi()
    {
        return new SpotifyApi.Builder()
            .setClientId(env.getProperty("spoticlean.spotify.clientId"))
            .setClientSecret(env.getProperty("spoticlean.spotify.clientSecret"))
            .setRedirectUri(URI.create(env.getProperty("spoticlean.spotify.redirectUri")))
            .build();
    }
}
