package com.kyriesenbach.mixmaster.playlist;

import com.kyriesenbach.mixmaster.search.SearchService;
import com.kyriesenbach.mixmaster.user.UserService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.TooManyRequestsException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService
{
    private final SpotifyApi spotifyApi;
    private final UserService userService;
    private final SearchService searchService;
    
    @Autowired
    public PlaylistService(SpotifyApi spotifyApi, UserService userService, SearchService searchService)
    {
        this.spotifyApi = spotifyApi;
        this.userService = userService;
        this.searchService = searchService;
    }
    
    public Playlist getPlaylist(String id) throws SpotifyWebApiException, ParseException, IOException
    {
        GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(id).build();
        return getPlaylistRequest.execute();
    }
    
    public List<Track> getPlaylistItems(String playlistId) throws ParseException, SpotifyWebApiException, IOException
    {
        ArrayList<Track> tracks = new ArrayList<>();
        int totalTracks = getPlaylist(playlistId).getTracks().getTotal();
        for (int offset = 0; tracks.size() < totalTracks; offset += 100)
        {
            final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(playlistId).offset(offset).build();
            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            Arrays.stream(playlistTrackPaging.getItems()).forEach(track -> tracks.add((Track) track.getTrack()));
        }
        return tracks;
    }
    
    public Paging<PlaylistSimplified> getCurrentUserPlaylists() throws ParseException, SpotifyWebApiException, IOException
    {
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi.getListOfCurrentUsersPlaylists().build();
        return getListOfCurrentUsersPlaylistsRequest.execute();
    }
    
    public Playlist createPlaylist(String name, boolean public_) throws ParseException, SpotifyWebApiException, IOException
    {
        final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userService.getCurrentUser().getId(), name).public_(public_).build();
        return createPlaylistRequest.execute();
    }
    
    private void addItemsToPlaylist(String playlistId, List<String> uris) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        // 93 at a time appears to be an arbitrary limit imposed by Spotify's API.
        // Using a value of more than 93 will cause the items to simply not be added.
        final int limit = 93;
        while (uris.size() > limit)
        {
            final String[] uriArray = uris.subList(0, limit).toArray(String[]::new);
            final AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(playlistId, uriArray).build();
            try
            {
                addItemsToPlaylistRequest.execute();
            }
            catch (TooManyRequestsException e)
            {
                Thread.sleep(e.getRetryAfter() * 1000L);
                addItemsToPlaylistRequest.execute();
            }
            uris = uris.subList(limit, uris.size());
        }
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(playlistId, uris.toArray(String[]::new)).build();
        addItemsToPlaylistRequest.execute();
    }
    
    public Playlist duplicatePlaylist(String oldPlaylistId, String newPlaylistName) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        final Playlist newPlaylist = createPlaylist(newPlaylistName, true);
        List<String> uris = getPlaylistItems(oldPlaylistId).stream().map(Track::getUri).collect(Collectors.toList());
        addItemsToPlaylist(newPlaylist.getId(), uris);
        return newPlaylist;
    }
    
    public Playlist cleanPlaylist(String oldPlaylistId, String cleanPlaylistName) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        List<Track> oldTracks = getPlaylistItems(oldPlaylistId);
        List<String> cleanUris = new ArrayList<>();
        for (Track track : oldTracks)
        {
            String query = "track:" + track.getName() + " artist:" + track.getArtists()[0].getName();
            Paging<Track> searchResults = searchService.searchTracks(query);
            for (Track result : searchResults.getItems())
            {
                if (track.getName().equals(result.getName())
                    && track.getArtists()[0].getName().equals(result.getArtists()[0].getName())
                    && track.getAlbum().getName().equals(result.getAlbum().getName())
                    && !result.getIsExplicit())
                {
                    cleanUris.add(result.getUri());
                    break;
                }
            }
        }
        Playlist cleanPlaylist = createPlaylist(cleanPlaylistName, true);
        addItemsToPlaylist(cleanPlaylist.getId(), cleanUris);
        return cleanPlaylist;
    }
}
