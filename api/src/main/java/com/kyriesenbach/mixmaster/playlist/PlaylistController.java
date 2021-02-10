package com.kyriesenbach.mixmaster.playlist;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final PlaylistService playlistService;
    
    @Autowired
    public PlaylistController(PlaylistService playlistService)
    {
        this.playlistService = playlistService;
    }
    
    @GetMapping("/{id}")
    public Playlist getPlaylist(@PathVariable("id") String id) throws SpotifyWebApiException, ParseException, IOException
    {
        return playlistService.getPlaylist(id);
    }
    
    @GetMapping("/{id}/items")
    public List<Track> getPlaylistItems(@PathVariable("id") String playlistId) throws ParseException, SpotifyWebApiException, IOException
    {
        return playlistService.getPlaylistItems(playlistId);
    }
    
    @PostMapping("/create")
    public Playlist createPlaylist(@RequestParam String name) throws ParseException, SpotifyWebApiException, IOException
    {
        return playlistService.createPlaylist(name, true);
    }
    
    @PostMapping("/{id}/duplicate")
    public Playlist duplicatePlaylist(
        @PathVariable("id") String oldPlaylistId,
        @RequestParam("newPlaylistName") String newPlaylistName) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        return playlistService.duplicatePlaylist(oldPlaylistId, newPlaylistName);
    }
    
    @PostMapping("/{id}/clean")
    public Playlist cleanPlaylist(
        @PathVariable("id") String oldPlaylistId,
        @RequestParam String cleanPlaylistName)
        throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        return playlistService.cleanPlaylist(oldPlaylistId, cleanPlaylistName);
    }
}
