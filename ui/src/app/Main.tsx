import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';

export default class Main extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        const { spotifyApi } = this.props;

        spotifyApi?.getAuthorizationUrl().then(response => {
            this.setState({ authUrl: response.data });
        });
    }

    render() {
        return (
            <a href={this.state.authUrl ?? 'https://google.com'}>sign in to spotify</a>
        );
    }
}

interface Props extends RouteComponentProps<any> {
    spotifyApi: SpotifyApi;
}

type State = {
    authUrl?: string;
};