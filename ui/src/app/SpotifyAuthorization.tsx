import cookie from 'cookie';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import { SPOTIFY_TOKEN } from '../constants';

export default function SpotifyAuthorization(props: Props) {
    const { location } = props;
    const query = new URLSearchParams(location.search);
    const authCode = query.get('code'); // Hardcoded because this parameter name is provided by Spotify and will not change

    if (authCode) {
        const { spotifyApi } = props;

        spotifyApi.getAccessToken(authCode);
    }

    const token = cookie.parse(document.cookie)[SPOTIFY_TOKEN];

    window.location.href = token ? "/spotify/playlistSelect" : "/";
    return null;
}

type Props = RouteComponentProps & {
    spotifyApi: SpotifyApi;
};