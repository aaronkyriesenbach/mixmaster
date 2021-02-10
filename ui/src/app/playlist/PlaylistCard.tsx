import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowAltCircleRight } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames';
import React from 'react';
import { Card } from 'react-bootstrap';
import { PLACEHOLDER_MUSIC_IMG } from '../../constants';
import { Playlist } from '../models/Playlist';
import './_styles.css';

export default class PlaylistCard extends React.Component<Props, State> {
    render() {
        const { playlist, onSelectPlaylist, selected, onSubmit } = this.props;

        return (
            <Card
                className={classNames('playlist-card', { 'clickable': onSelectPlaylist }, { selected })}
                onClick={onSelectPlaylist ? () => onSelectPlaylist(playlist.id) : undefined}
            >
                <Card.Img className='playlist-card image' src={playlist.images[0].url ?? PLACEHOLDER_MUSIC_IMG} />
                <Card.ImgOverlay className='playlist-card name'>{playlist.name}</Card.ImgOverlay>
                {selected && onSubmit &&
                    <Card.ImgOverlay>
                        <button onClick={() => onSubmit()}><FontAwesomeIcon icon={faArrowAltCircleRight} /></button>
                    </Card.ImgOverlay>
                }
            </Card>
        );
    }
}

type Props = {
    playlist: Playlist;
    onSelectPlaylist?: (id: string) => void;
    selected?: boolean;
    onSubmit?: () => void;
};

type State = {};