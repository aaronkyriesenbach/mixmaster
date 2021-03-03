import React from 'react';
import { Card } from 'react-bootstrap';
import { Playlist } from '../models/Playlist';
import placeholder from './placeholder.png';
import './_styles.css';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist, onClick } = this.props;

        var playlistImage = undefined;

        for (let i = 0; i < playlist.images.length; i++) {
            const image = playlist.images[i];

            if (image.height === image.width) {
                playlistImage = image;
                break;
            }
        }

        return (
            <Card
                className='playlist-card'
                onClick={onClick ? () => onClick(playlist.id) : () => window.open(`https://open.spotify.com/playlist/${playlist.id}`, '_blank')}
            >
                <Card.Img className='playlist-card image' src={playlistImage ? playlistImage.url : placeholder} />
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