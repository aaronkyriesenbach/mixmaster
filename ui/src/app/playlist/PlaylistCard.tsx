import React from 'react';
import { Playlist } from '../models/Playlist';
import './_styles.css';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist } = this.props;

        return (
            <div
                className='playlist-card image'
                style={{ backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url(${playlist.images[0].url})` }}
            >
                <p className='playlist-card name'>{playlist.name}</p>
            </div>
        );
    }
}

type Props = {
    playlist: Playlist;
    onSelectPlaylist?: (event: React.FormEvent) => void;
};

type State = {};