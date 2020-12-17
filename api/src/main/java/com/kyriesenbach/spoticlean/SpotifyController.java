package com.kyriesenbach.spoticlean;

import com.kyriesenbach.spoticlean.exceptions.PlaylistNotFoundException;
import com.wrapper.spotify.model_objects.specification.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController("/spotify")
public class SpotifyController
{
    private final SpotifyService spotifyService;
    
    @Autowired
    public SpotifyController(SpotifyService spotifyService)
    {
        this.spotifyService = spotifyService;
    }
    
    @GetMapping("/authorize")
    public URI authorize()
    {
        return spotifyService.authorize();
    }
    
    @PostMapping("/authorize")
    public void setAccessToken(@RequestParam String token)
    {
        spotifyService.setAccessToken(token);
    }
    
    @GetMapping("/playlist")
    public Playlist getPlaylist(@RequestParam String id) throws PlaylistNotFoundException
    {
        return spotifyService.getPlaylist(id);
    }
}
