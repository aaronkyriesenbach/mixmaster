import { faArrowAltCircleRight } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Button, Col, Container, Row, Toast } from 'react-bootstrap';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../../api/SpotifyApi';
import Loading from '../loading/Loading';
import { Playlist } from '../models/Playlist';
import PlaylistCard from '../playlist/PlaylistCard';
import './_styles.css';

export default class Clean extends React.Component<Props, State> {
    componentWillMount() {
        const { spotifyApi } = this.props;
        const playlistId = this.props.match.params.id;

        spotifyApi.getPlaylist(playlistId)
            .then(response => {
                const playlist = response.data as Playlist;
                this.setState({ playlist: playlist });
            });
    }

    // TODO: Add validation once I can find Spotify's documentation for what's allowed
    onChangeName = (event: React.ChangeEvent) => {
        const element = event.target as HTMLInputElement;
        const name = element.value;

        this.setState({ newName: name });
    };

    cleanPlaylist = () => {
        const { spotifyApi } = this.props;
        const { playlist, newName } = this.state;

        this.setState({ started: true });
        spotifyApi.cleanPlaylist(playlist?.id, newName!)
            .then(res => {
                const newPlaylist: Playlist = res.data;

                window.location.href = `/spotify/postClean?playlistId=${newPlaylist.id}`;
            })
            .catch(err => this.setState({ error: err }));
    };

    render() {
        const { newName, started, playlist, error } = this.state || {};
        const { onChangeName, cleanPlaylist } = this;

        if (started) {
            return <Loading message="Cleaning in progress..." />;
        }
        if (playlist) {
            return (
                <Container fluid>
                    {started && <p>Cleaning in progress...</p>}
                    {error &&
                        <Toast>
                            <Toast.Header>
                                <header>Failed to clean playlist</header>
                            </Toast.Header>
                            <Toast.Body>{error.data}</Toast.Body>
                        </Toast>}
                    <Row>
                        <Col md='5'>
                            <PlaylistCard playlist={playlist} />
                        </Col>
                        <Col>
                            <Row className='new-name-container'>
                                <Col className='new-name instructions'>
                                    <header>what would you like to call your new playlist?</header>
                                </Col>
                                <Col className='new-name'>
                                    <input type='text' className='new-name input' onChange={onChangeName} />
                                    <Button className='submit-button' disabled={!newName} onClick={cleanPlaylist}>
                                        <FontAwesomeIcon icon={faArrowAltCircleRight} />
                                    </Button>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                </Container>
            );
        }
        return (<Loading />);
    }
}

export type Props = RouteComponentProps<{ id: string; }> & {
    spotifyApi: SpotifyApi;
};

type State = {
    playlist: Playlist;
    newName?: string;
    error?: any;
    started: boolean;
};