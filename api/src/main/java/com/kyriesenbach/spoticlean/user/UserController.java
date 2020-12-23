package com.kyriesenbach.spoticlean.user;

import com.kyriesenbach.spoticlean.playlist.PlaylistService;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/spotify/user")
@Slf4j
public class UserController
{
    private final UserService userService;
    private final PlaylistService playlistService;
    
    @Autowired
    public UserController(UserService userService, PlaylistService playlistService)
    {
        this.userService = userService;
        this.playlistService = playlistService;
    }
    
    @GetMapping("/current")
    public User getCurrentUser(@RequestHeader(name = "Authorization") String accessToken) throws ParseException, SpotifyWebApiException, IOException
    {
        return userService.getCurrentUser(accessToken);
    }
    
    @GetMapping("/playlists")
    public Paging<PlaylistSimplified> getCurrentUserPlaylists(@RequestHeader(name = "Authorization") String accessToken) throws ParseException, SpotifyWebApiException, IOException
    {
        return playlistService.getCurrentUserPlaylists(accessToken);
    }
}
