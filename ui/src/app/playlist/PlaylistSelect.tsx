import React from 'react';
import { Container, Row } from 'react-bootstrap';
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

    onSelectPlaylist = (id: string) => {
        window.location.href = `/spotify/${id}/clean`;
    };

    render() {
        const { playlists } = this.state;
        const { onSelectPlaylist } = this;

        const playlistCards = playlists.map(playlist =>
            <PlaylistCard
                key={playlist.id}
                playlist={playlist}
                onClick={onSelectPlaylist}
            />
        );

        var rows = [];
        for (var i = 0; i < playlistCards.length; i += 3) {
            rows.push(<Row key={i} md='3'>{playlistCards.slice(i, i + 3)}</Row>);
        }

        return (
            <Container className='playlist-container'>
                {rows}
            </Container>
        );
    }
}

type Props = {
    spotifyApi: SpotifyApi;
};

type State = {
    playlists: Playlist[];
};