import classnames from 'classnames';
import React from 'react';
import { Card } from 'react-bootstrap';
import { Playlist } from '../models/Playlist';
import placeholder from './placeholder.png';
import './_styles.scss';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist, onClick, large } = this.props;

        return (
            <Card
                className={classnames('playlist-card m-2', { large })}
                onClick={onClick ? () => onClick(playlist.id) : () => window.open(`https://open.spotify.com/playlist/${playlist.id}`, '_blank')}
            >
                <Card.Img className='playlist-image' src={playlist.images[0] ? playlist.images[0].url : placeholder} />
                <Card.ImgOverlay className='playlist-name'>{playlist.name}</Card.ImgOverlay>
            </Card>
        );
    }
}

type Props = {
    playlist: Playlist;
    onClick?: (id: string) => void;
    large?: boolean;
};

type State = {};