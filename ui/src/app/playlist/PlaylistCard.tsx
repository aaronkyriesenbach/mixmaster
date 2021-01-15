import React from 'react';
import { Playlist } from '../models/Playlist';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist } = this.props;

        return (
            <React.Fragment>
                <header>{playlist.name}</header>
                <input type='radio' />
            </React.Fragment>
        );
    }
}

type Props = {
    playlist: Playlist;
};

type State = {};