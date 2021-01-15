import React from 'react';
import SpotifyApi from '../../api/SpotifyApi';
import { mapPlaylists } from '../models/Mappers';
import { Playlist } from '../models/Playlist';
import PlaylistCard from './PlaylistCard';

export default class PlaylistSelect extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            playlists: []
        };
    }

    componentDidMount() {
        const { spotifyApi } = this.props;

        spotifyApi.getCurrentUserPlaylists()
            .then(response => {
                const playlists = response.data.items.map(mapPlaylists);
                this.setState({ playlists: playlists });
            });
    }

    render() {
        const { playlists } = this.state || [];

        return playlists.map(playlist => <PlaylistCard playlist={playlist} />);
    }
}

type Props = {
    spotifyApi: SpotifyApi;
};

type State = {
    playlists: Playlist[];
};