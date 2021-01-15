import { BaseSpotifyEntity } from "./BaseSpotifyEntity";

export type Playlist = BaseSpotifyEntity & {
    images: any,
    isCollaborative: boolean,
    isPublicAccess: boolean,
    owner: any,
    snapshotId: string,
    tracks: any,
    uri: string;
};