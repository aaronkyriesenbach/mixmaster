import { Playlist } from './Playlist';
import { Image } from './Image';

export const mapPlaylists = (playlist: any): Playlist => {
    return {
        externalUrls: playlist.externalUrls,
        href: playlist.href,
        id: playlist.id,
        images: playlist.images as Image[],
        isCollaborative: playlist.isCollaborative,
        isPublicAccess: playlist.isPublicAccess,
        name: playlist.name,
        owner: playlist.owner,
        snapshotId: playlist.snapshotId,
        tracks: playlist.tracks,
        uri: playlist.uri
    };
};