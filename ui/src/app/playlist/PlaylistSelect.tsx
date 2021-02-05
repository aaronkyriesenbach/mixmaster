import React from 'react';
import { Container } from 'react-bootstrap';
import SpotifyApi from '../../api/SpotifyApi';
import { mapPlaylists } from '../models/Mappers';
import { Playlist } from '../models/Playlist';
import PlaylistCard from './PlaylistCard';

export default class PlaylistSelect extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            playlists: [],
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

    onSelectPlaylist = (event: React.FormEvent) => {
        const selectedValue = (event.target as HTMLInputElement).value;

        this.setState({ selectedPlaylist: selectedValue });
    };

    onSubmit = () => {
        const { selectedPlaylist } = this.state;

        window.location.href = `/spotify/${selectedPlaylist}/clean`;
    };

    render() {
        const { playlists, selectedPlaylist } = this.state;
        const { onSelectPlaylist, onSubmit } = this;

        const playlistCards = playlists.map(playlist =>
            <PlaylistCard
                key={playlist.id}
                playlist={playlist}
                onSelectPlaylist={onSelectPlaylist}
            />
        );

        return (
            <Container>
                {playlistCards}
                <br />
                <input type='submit' disabled={!selectedPlaylist} onClick={onSubmit} />
            </Container>
        );
    }
}

type Props = {
    spotifyApi: SpotifyApi;
};

type State = {
    playlists: Playlist[];
    selectedPlaylist?: string;
};