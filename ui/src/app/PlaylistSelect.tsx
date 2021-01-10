import React from 'react';
import SpotifyApi from '../api/SpotifyApi';

export default class PlaylistSelect extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        const { spotifyApi } = this.props;

        spotifyApi.getCurrentUserPlaylists()
            .then(response => console.log(response));
    }

    render() {
        return <header>test text</header>;
    }
}

type Props = {
    spotifyApi: SpotifyApi;
};

type State = {};