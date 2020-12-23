package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class ApplicationConfiguration
{
    @Bean
    public SpotifyApi spotifyApi()
    {
        return new SpotifyApi.Builder()
            .setClientId(System.getenv("SPOTIFY_CLIENT_ID"))
            .setClientSecret(System.getenv("SPOTIFY_CLIENT_SECRET"))
            .setRedirectUri(URI.create("http://localhost"))
            .build();
    }
}
