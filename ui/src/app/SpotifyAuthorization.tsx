import cookie from 'cookie';
import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';

export default class SpotifyAuthorization extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    componentWillMount() {
        const { location, spotifyApi } = this.props;
        const query = new URLSearchParams(location.search);
        const authCode = query.get('code');
        const token = cookie.parse(document.cookie)['token'];

        if (token !== undefined) {
            window.location.href = "/spotify/playlistSelect";
        }
        else if (authCode !== null) {
            spotifyApi.getAccessToken(authCode);
        }
    }

    render() {
        const token = cookie.parse(document.cookie)['spotify_token'];

        if (token !== undefined) {
            window.location.href = "/spotify/playlistSelect";
        }
        else {
            window.location.href = "/";
        }
        return null;
    }
}

interface Props extends RouteComponentProps<any> {
    spotifyApi: SpotifyApi;
}

type State = {};