import React from 'react';
import SpotifyApi from '../api/SpotifyApi';

export default class Authorization extends React.Component<Props, State> {
    state: State = {}

    componentDidMount() {
        const { location } = this.props;
        const query = new URLSearchParams(location.search);
        const authCode = query.get('code');

        this.setState({
            spotifyApi: new SpotifyApi(),
            authCode: authCode !== null ? authCode : undefined
        });
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        const { authCode, spotifyApi } = this.state;

        if (prevState.authCode !== authCode && authCode !== undefined) {
            spotifyApi!.getAccessToken(this.state.authCode!)
                .then(response => this.setState({ accessToken: response.data }));
        }
    }

    render() {
        // const { authCode, accessToken } = this.state;

        return this.state.accessToken !== undefined ?
            <header>Your access token is {this.state.accessToken}</header>
            :
            <header>Your auth code is {this.state.authCode}</header>;
    }
}

type Props = {
    location: Location;
};

type State = {
    spotifyApi?: SpotifyApi,
    authCode?: string,
    accessToken?: string;
};