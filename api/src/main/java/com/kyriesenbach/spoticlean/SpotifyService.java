package com.kyriesenbach.spoticlean;

import com.kyriesenbach.spoticlean.exceptions.PlaylistNotFoundException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    
    public URI authorize()
    {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().build();
        return authorizationCodeUriRequest.execute();
    }
    
    public void setAccessToken(String token)
    {
        spotifyApi.setAccessToken(token);
        log.info("Successfully set access token " + token);
    }
    
    public Playlist getPlaylist(String id) throws PlaylistNotFoundException
    {
        GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(id).build();
        try
        {
            return getPlaylistRequest.execute();
        }
        catch (Exception e)
        {
            throw new PlaylistNotFoundException(id);
        }
    }
}
