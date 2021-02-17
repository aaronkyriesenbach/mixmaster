import React from 'react';
import { Card } from 'react-bootstrap';
import { Playlist } from '../models/Playlist';
import placeholder from './placeholder.png';
import './_styles.css';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist, onClick } = this.props;

        return (
            <Card
                className={'playlist-card'}
                onClick={onClick ? () => onClick(playlist.id) : () => window.open(`https://open.spotify.com/playlist/${playlist.id}`, '_blank')}
            >
                <Card.Img className='playlist-card image' src={playlist.images[0] ? playlist.images[0].url : placeholder} />
                <Card.ImgOverlay className='playlist-card name'>{playlist.name}</Card.ImgOverlay>
            </Card>
        );
    }
}

type Props = {
    playlist: Playlist;
    onClick?: (id: string) => void;
};

type State = {};