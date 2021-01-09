import cookie from 'cookie';
import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';

export default class Start extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            authorized: false
        };
    }

    componentWillMount() {
        const token = cookie.parse(document.cookie)['token'];

        if (token !== undefined) {
            this.setState({ authorized: true });
        }
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
        const { authorized, authUrl } = this.state;

        if (!authorized) {
            return (
                <a href={authUrl ?? 'https://google.com'}>sign in to spotify</a>
            );
        }
        else {
            window.location.href = "/spotify/playlistSelect";
        }
    }
}

interface Props extends RouteComponentProps<any> {
    spotifyApi: SpotifyApi;
}

type State = {
    authUrl?: string;
    authorized: boolean;
};