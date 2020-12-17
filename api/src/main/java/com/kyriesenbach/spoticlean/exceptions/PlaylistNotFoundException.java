package com.kyriesenbach.spoticlean.exceptions;

import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "No such playlist")
public class PlaylistNotFoundException extends NotFoundException
{
    public PlaylistNotFoundException(String id)
    {
        super("Playlist with id " + id + " not found");
    }
}
