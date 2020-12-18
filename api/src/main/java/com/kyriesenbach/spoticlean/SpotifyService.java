package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.TooManyRequestsException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
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
    
    public Paging<Track> searchTracks(String accessToken, String query) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        spotifyApi.setAccessToken(accessToken);
        final SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query).build();
        try
        {
            return searchTracksRequest.execute();
        }
        catch (TooManyRequestsException e)
        {
            Thread.sleep(e.getRetryAfter() * 1000L);
            return searchTracksRequest.execute();
        }
    }
    
    public void addItemsToPlaylist(String accessToken, String playlistId, List<String> uris) throws ParseException, SpotifyWebApiException, IOException
    {
        spotifyApi.setAccessToken(accessToken);
        while (uris.size() > 100)
        {
            final AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(playlistId, uris.subList(0, 100).toArray(String[]::new)).build();
            addItemsToPlaylistRequest.execute();
            uris = uris.subList(100, uris.size());
            log.info("Added to playlist");
        }
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(playlistId, uris.toArray(String[]::new)).build();
        addItemsToPlaylistRequest.execute();
    }
    
    public List<Track> getPlaylistTracks(String accessToken, String playlistId) throws ParseException, SpotifyWebApiException, IOException
    {
        spotifyApi.setAccessToken(accessToken);
        ArrayList<Track> tracks = new ArrayList<>();
        int totalTracks = getPlaylist(accessToken, playlistId).getTracks().getTotal();
        for (int offset = 0; tracks.size() < totalTracks; offset += 100)
        {
            final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(playlistId).offset(offset).build();
            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            Arrays.stream(playlistTrackPaging.getItems()).forEach(track -> tracks.add((Track) track.getTrack()));
        }
        return tracks;
    }
    
    public Playlist cleanPlaylist(String accessToken, String oldPlaylistId, String cleanPlaylistName) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        spotifyApi.setAccessToken(accessToken);
        List<Track> oldTracks = getPlaylistTracks(accessToken, oldPlaylistId);
        List<String> cleanUris = new ArrayList<>();
        for (Track track : oldTracks)
        {
            log.info("Attempting to add " + track.getName());
            Paging<Track> searchResults = searchTracks(accessToken, "track:" + track.getName() + " artist:" + track.getArtists()[0].getName());
            for (Track result : searchResults.getItems())
            {
                if (track.getName().equals(result.getName())
                    && track.getArtists()[0].getName().equals(result.getArtists()[0].getName())
                    && track.getAlbum().getName().equals(result.getAlbum().getName())
                    && !result.getIsExplicit())
                {
                    log.info("Adding clean version " + result.getName());
                    cleanUris.add(result.getUri());
                    break;
                }
            }
        }
        Playlist cleanPlaylist = createPlaylist(accessToken, cleanPlaylistName, true);
        addItemsToPlaylist(accessToken, cleanPlaylist.getId(), cleanUris);
        return cleanPlaylist;
    }
}
