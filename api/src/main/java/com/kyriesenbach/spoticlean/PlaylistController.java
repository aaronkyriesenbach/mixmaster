package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/spotify/playlist")
@Slf4j
public class PlaylistController
{
    private final SpotifyService spotifyService;
    
    @Autowired
    public PlaylistController(SpotifyService spotifyService)
    {
        this.spotifyService = spotifyService;
    }
    
    @GetMapping
    public Playlist getPlaylist(@RequestHeader(name = "Authorization") String accessToken, @RequestParam String id) throws SpotifyWebApiException, ParseException, IOException
    {
        return spotifyService.getPlaylist(accessToken, id);
    }
    
    @GetMapping("/current")
    public Paging<PlaylistSimplified> getCurrentUserPlaylists(@RequestHeader(name = "Authorization") String accessToken) throws ParseException, SpotifyWebApiException, IOException
    {
        return spotifyService.getCurrentUserPlaylists(accessToken);
    }
    
    @PostMapping("/create")
    public Playlist createPlaylist(@RequestHeader(name = "Authorization") String accessToken, @RequestParam String name) throws ParseException, SpotifyWebApiException, IOException
    {
        return spotifyService.createPlaylist(accessToken, name, true);
    }
    
    @PostMapping("/clean")
    public Playlist cleanPlaylist(
        @RequestHeader(name = "Authorization") String accessToken,
        @RequestParam String oldPlaylistId,
        @RequestParam String cleanPlaylistName)
        throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        return spotifyService.cleanPlaylist(accessToken, oldPlaylistId, cleanPlaylistName);
    }
    
    @GetMapping("/{id}/tracks")
    public List<Track> getTracks(
        @RequestHeader(name = "Authorization") String accessToken,
        @PathVariable("id") String playlistId)
        throws ParseException, SpotifyWebApiException, IOException
    {
        return spotifyService.getPlaylistTracks(accessToken, playlistId);
    }
}
