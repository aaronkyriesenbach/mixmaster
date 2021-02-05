import { BaseSpotifyEntity } from './BaseSpotifyEntity';
import { Image } from './Image';

export type Playlist = BaseSpotifyEntity & {
    images: Image[],
    isCollaborative: boolean,
    isPublicAccess: boolean,
    owner: any,
    snapshotId: string,
    tracks: any,
    uri: string;
};