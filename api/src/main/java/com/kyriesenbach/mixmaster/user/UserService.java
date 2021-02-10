package com.kyriesenbach.mixmaster.user;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService
{
    private final SpotifyApi spotifyApi;
    
    @Autowired
    public UserService(SpotifyApi spotifyApi)
    {
        this.spotifyApi = spotifyApi;
    }
    
    public User getCurrentUser() throws ParseException, SpotifyWebApiException, IOException
    {
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        return getCurrentUsersProfileRequest.execute();
    }
}
