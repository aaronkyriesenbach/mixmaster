package com.kyriesenbach.spoticlean;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/spotify/search")
public class SearchController
{
    private final SpotifyService spotifyService;
    
    @Autowired
    public SearchController(SpotifyService spotifyService)
    {
        this.spotifyService = spotifyService;
    }
    
    @GetMapping("/track")
    public Paging<Track> searchTracks(@RequestHeader(name = "Authorization") String accessToken, String query) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        return spotifyService.searchTracks(accessToken, query);
    }
}
