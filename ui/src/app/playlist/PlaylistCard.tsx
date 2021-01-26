import React from 'react';
import { Playlist } from '../models/Playlist';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist, onSelectPlaylist, selected } = this.props;

        return (
            <React.Fragment>
                <header>{playlist.name}</header>
                <input type='radio' value={playlist.id} checked={selected} onChange={onSelectPlaylist} />
            </React.Fragment>
        );
    }
}

type Props = {
    playlist: Playlist;
    onSelectPlaylist: (event: React.FormEvent) => void;
    selected: boolean;
};

type State = {};