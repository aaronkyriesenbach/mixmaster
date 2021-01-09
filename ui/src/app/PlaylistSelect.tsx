import React from 'react';
import SpotifyApi from '../api/SpotifyApi';

export default class PlaylistSelect extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    render() {
        return <header>test text</header>;
    }
}

type Props = {
    spotifyApi: SpotifyApi;
};

type State = {};