import { faMusic } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Button, Col, Container } from 'react-bootstrap';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../../api/SpotifyApi';
import { Playlist } from '../models/Playlist';
import PlaylistCard from '../playlist/PlaylistCard';
import './_styles.scss';

export default class PostClean extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        const { location } = this.props;
        const query = new URLSearchParams(location.search);
        this.state = {
            playlistId: query.get('playlistId') || undefined
        };
    }

    componentDidMount() {
        const { spotifyApi } = this.props;
        const { playlistId } = this.state;

        if (playlistId) {
            spotifyApi.getPlaylist(playlistId)
                .then(res => this.setState({ playlist: res.data as Playlist }));
        }
        else {
            window.location.href = '/spotify/playlistSelect';
        }
    }

    render() {
        const { playlist } = this.state;

        return (
            <Container fluid className='clean-container d-flex flex-row align-items-center'>
                <Col className='d-flex justify-content-center'>
                    {playlist && <PlaylistCard large playlist={playlist} />}
                </Col>
                <Col className='cleaned-text'>
                    <header>enjoy your new playlist!</header>
                    <header>clean another?</header>
                    <Button className='again-button' onClick={() => window.location.href = '/spotify/playlistSelect'}>
                        <FontAwesomeIcon icon={faMusic} size='2x' />
                    </Button>
                </Col>
            </Container>
        );
    }
}

type Props = RouteComponentProps & {
    spotifyApi: SpotifyApi;
};

type State = {
    playlistId?: string;
    playlist?: Playlist;
};