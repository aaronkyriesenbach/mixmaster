import cookie from 'cookie';
import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import { SPOTIFY_TOKEN } from '../constants';

export default class SpotifyAuthorization extends React.Component<Props, {}> {
    async componentDidMount() {
        const { location } = this.props;
        const query = new URLSearchParams(location.search);
        const authCode = query.get('code'); // Hardcoded because this parameter name is provided by Spotify and will not change

        if (authCode) {
            const { spotifyApi } = this.props;

            await spotifyApi.getAccessToken(authCode);
        }

        const token = cookie.parse(document.cookie)[SPOTIFY_TOKEN];

        window.location.href = token ? "/spotify/playlistSelect" : "/";
    }

    render() {
        return null;
    }
}

type Props = RouteComponentProps & {
    spotifyApi: SpotifyApi;
};