import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';

export default class Authorization extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        const { location } = this.props;
        const query = new URLSearchParams(location.search);
        const authCode = query.get('code');

        this.setState({
            authCode: authCode !== null ? authCode : undefined
        });
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        const { authCode } = this.state;
        const { spotifyApi } = this.props;

        if (prevState.authCode !== authCode && authCode !== undefined) {
            spotifyApi!.getAccessToken(authCode!)
                .then(response => {
                    console.log("Headers:");
                    console.log(response.headers);
                    this.setState({ accessToken: response.data });
                });
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

interface Props extends RouteComponentProps<any> {
    spotifyApi: SpotifyApi;
}

type State = {
    authCode?: string,
    accessToken?: string;
};