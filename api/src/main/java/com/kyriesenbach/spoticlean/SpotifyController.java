package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
        return spotifyService.getAuthURL();
    }
    
    @GetMapping("/accesstoken")
    public String getAccessToken(@RequestParam String authorizationCode) throws SpotifyWebApiException, ParseException, IOException
    {
        return spotifyService.getAccessToken(authorizationCode);
    }
    
    @GetMapping("/playlist")
    public Playlist getPlaylist(@RequestHeader(name = "Authorization") String accessToken, @RequestParam String id) throws SpotifyWebApiException, ParseException, IOException
    {
        return spotifyService.getPlaylist(accessToken, id);
    }
    
    @GetMapping("/playlist/current")
    public Paging<PlaylistSimplified> getCurrentUserPlaylists(@RequestHeader(name = "Authorization") String accessToken) throws ParseException, SpotifyWebApiException, IOException
    {
        return spotifyService.getCurrentUserPlaylists(accessToken);
    }
    
    @PostMapping("/playlist/create")
    public Playlist createPlaylist(@RequestHeader(name = "Authorization") String accessToken, @RequestParam String name) throws ParseException, SpotifyWebApiException, IOException
    {
        return spotifyService.createPlaylist(accessToken, name, true);
    }
}
