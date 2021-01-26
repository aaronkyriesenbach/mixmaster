import React from 'react';
import SpotifyApi from '../api/SpotifyApi';
import { RouteComponentProps } from 'react-router-dom';

export default class Clean extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            playlistId: this.props.match.params.id
        };

    }

    onChangeName = (event: React.ChangeEvent) => {
        const element = event.target as HTMLInputElement;
        const name = element.value;

        this.setState({ newName: name });
    };

    onSubmit = (id: string) => {
        console.log(id);
    };

    render() {
        const { newName } = this.state;
        const { onChangeName } = this;

        return (
            <React.Fragment>
                <header>New playlist name:</header>
                <input type='text' onChange={onChangeName} />
                <input type='submit' disabled={!newName} />
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
};