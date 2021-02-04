import { faSpotify } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import './_styles.css';

export default class Start extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            authUrl: undefined
        };
    }

    componentDidMount() {
        const { spotifyApi } = this.props;
        const { authUrl } = this.state;

        if (authUrl === undefined) {
            spotifyApi.getAuthorizationUrl()
                .then(response => this.setState({ authUrl: response.data }));
        }
    }

    render() {
        const { authUrl } = this.state;

        return (
            <Container fluid>
                <Col className='col-sign-in'>
                    <Row>
                        <Button
                            variant='sign-in spotify'
                            disabled={!authUrl}
                            size='lg'
                            href={authUrl ?? '#'}
                        >
                            <FontAwesomeIcon icon={faSpotify} /> {authUrl ? 'Sign in to Spotify' : 'Loading...'}
                        </Button >
                    </Row>
                </Col>
            </Container>
        );
    }
}

interface Props extends RouteComponentProps<any> {
    spotifyApi: SpotifyApi;
}

type State = {
    authUrl?: string;
};