package com.kyriesenbach.spoticlean.search;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.TooManyRequestsException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SearchService
{
    private final SpotifyApi spotifyApi;
    
    @Autowired
    public SearchService(SpotifyApi spotifyApi)
    {
        this.spotifyApi = spotifyApi;
    }
    
    public Paging<Track> searchTracks(String query) throws ParseException, SpotifyWebApiException, IOException, InterruptedException
    {
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
}
