import axios, { AxiosInstance, AxiosResponse } from 'axios';
import { BASE_API_URL } from '../constants';

export default class SpotifyApi {
    api: AxiosInstance;

    constructor() {
        this.api = axios.create({ baseURL: BASE_API_URL + "/spotify" });
    }

    public getAuthorizationUrl(): Promise<AxiosResponse<string>> {
        return this.api.get("/auth/url");
    }

    public getAccessToken(authorizationCode: string): Promise<AxiosResponse<string>> {
        return this.api.get("/auth/token?code=" + authorizationCode);
    }
}    