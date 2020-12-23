package com.kyriesenbach.spoticlean.auth;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

@Service
public class AuthService
{
    private final SpotifyApi spotifyApi;
    
    @Autowired
    public AuthService(SpotifyApi spotifyApi)
    {
        this.spotifyApi = spotifyApi;
    }
    
    public URI getAuthURL()
    {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("playlist-modify-public, playlist-modify-private").build();
        return authorizationCodeUriRequest.execute();
    }
    
    public String getAccessToken(String authorizationCode) throws SpotifyWebApiException, ParseException, IOException
    {
        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(authorizationCode).build();
        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
        return authorizationCodeCredentials.getAccessToken();
    }
    
    public User getCurrentUser() throws ParseException, SpotifyWebApiException, IOException
    {
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        return getCurrentUsersProfileRequest.execute();
    }
}
