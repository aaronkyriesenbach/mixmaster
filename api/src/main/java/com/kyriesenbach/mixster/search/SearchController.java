package com.kyriesenbach.mixster.search;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/spotify/search")
public class SearchController
{
    private final SearchService searchService;
    
    @Autowired
    public SearchController(SearchService searchService)
    {
        this.searchService = searchService;
    }
    
    @GetMapping("/track")
    public Paging<Track> searchTracks(String query) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
        return searchService.searchTracks(query);
    }
}
