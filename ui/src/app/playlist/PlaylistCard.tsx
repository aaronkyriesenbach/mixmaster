import React from 'react';
import { Playlist } from '../models/Playlist';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist } = this.props;

        return (
            <React.Fragment>
                <a href={`https://open.spotify.com/playlist/${playlist.id}`} target='_blank' rel='noreferrer'>{playlist.name}</a>
            </React.Fragment>
        );
    }
}

type Props = {
    playlist: Playlist;
};

type State = {};