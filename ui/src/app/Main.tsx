import React from 'react';
import SpotifyApi from '../api/SpotifyApi';

export default class Main extends React.Component<Props, State> {
    state: State = {};

    componentDidMount() {
        this.setState({ spotifyApi: new SpotifyApi() });
    }

    componentDidUpdate() {
        const { spotifyApi } = this.state;

        spotifyApi?.getAuthorizationUrl().then(response => {
            console.log(response);
            this.setState({ authUrl: response.data });
        });
    }

    render() {
        const { authUrl } = this.state;

        return (
            <a href={authUrl ?? 'https://google.com'}>sign in to spotify</a>
        );
    }
}

type Props = {};

type State = {
    spotifyApi?: SpotifyApi,
    authUrl?: string;
};