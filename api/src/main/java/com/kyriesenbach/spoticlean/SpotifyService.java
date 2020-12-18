package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

@Service
@Slf4j
public class SpotifyService
{
    SpotifyApi spotifyApi = new SpotifyApi.Builder()
        .setClientId(System.getenv("SPOTIFY_CLIENT_ID"))
        .setClientSecret(System.getenv("SPOTIFY_CLIENT_SECRET"))
        .setRedirectUri(URI.create("http://localhost"))
        .build();
    
    public URI getAuthURL()
    {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("playlist-modify-public, playlist-modify-private").build();
        return authorizationCodeUriRequest.execute();
    }
    
    public String getAccessToken(String authorizationCode) throws SpotifyWebApiException, ParseException, IOException
    {
        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(authorizationCode).build();
        final AuthorizationCodeCredentials authorizationCodeCredentials;
        authorizationCodeCredentials = authorizationCodeRequest.execute();
        log.info("Access token expires in: " + authorizationCodeCredentials.getExpiresIn());
        return authorizationCodeCredentials.getAccessToken();
    }
    
    public Playlist getPlaylist(String accessToken, String id) throws SpotifyWebApiException, ParseException, IOException
    {
        spotifyApi.setAccessToken(accessToken);
        GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(id).build();
        return getPlaylistRequest.execute();
    }
    
    public Paging<PlaylistSimplified> getCurrentUserPlaylists(String accessToken) throws ParseException, SpotifyWebApiException, IOException
    {
        spotifyApi.setAccessToken(accessToken);
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi.getListOfCurrentUsersPlaylists().build();
        return getListOfCurrentUsersPlaylistsRequest.execute();
    }
    
    private User getCurrentUser() throws ParseException, SpotifyWebApiException, IOException
    {
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        return getCurrentUsersProfileRequest.execute();
    }
    
    public Playlist createPlaylist(String accessToken, String name, boolean public_) throws ParseException, SpotifyWebApiException, IOException
    {
        spotifyApi.setAccessToken(accessToken);
        final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(getCurrentUser().getId(), name).public_(public_).build();
        return createPlaylistRequest.execute();
    }
}
