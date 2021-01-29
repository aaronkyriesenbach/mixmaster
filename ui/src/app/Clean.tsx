import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import { Playlist } from './models/Playlist';
import PlaylistCard from './playlist/PlaylistCard';

export default class Clean extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            playlistId: this.props.match.params.id,
            started: false
        };

    }

    // TODO: Add validation once I can find Spotify's documentation for what's allowed
    onChangeName = (event: React.ChangeEvent) => {
        const element = event.target as HTMLInputElement;
        const name = element.value;

        this.setState({ newName: name });
    };

    cleanPlaylist = () => {
        const { spotifyApi } = this.props;
        const { playlistId, newName } = this.state;

        this.setState({ started: true });
        spotifyApi.cleanPlaylist(playlistId, newName!)
            .then(res => {
                const newPlaylist: Playlist = res.data;

                this.setState({ cleanedPlaylist: newPlaylist });
            })
            .catch(err => this.setState({ error: err }));
    };

    renderResults = () => {
        const { error, cleanedPlaylist } = this.state;

        if (error) {
            return (
                <React.Fragment>
                    <header>Failed to clean playlist</header>
                    {error}
                </React.Fragment>
            );
        }
        if (cleanedPlaylist) {
            return (
                <React.Fragment>
                    <header>Cleaned playlist:</header>
                    <PlaylistCard playlist={cleanedPlaylist} />
                </React.Fragment>
            );
        }
        else {
            return (
                <header>Cleaning in progress...</header>
            );
        }
    };

    render() {
        const { newName, started } = this.state;
        const { onChangeName, cleanPlaylist, renderResults } = this;

        if (started) {
            return renderResults();
        }
        return (
            <React.Fragment>
                <header>New playlist name:</header>
                <input type='text' onChange={onChangeName} />
                <input type='submit' disabled={!newName} onClick={cleanPlaylist} />
            </React.Fragment>
        );
    }
}

export type Props = RouteComponentProps<{ id: string; }> & {
    spotifyApi: SpotifyApi;
};

type State = {
    playlistId: string;
    newName?: string;
    error?: any;
    started: boolean;
    cleanedPlaylist?: Playlist;
};