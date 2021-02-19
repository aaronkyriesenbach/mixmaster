export const BASE_API_URL = process.env.NODE_ENV === 'development' ? (process.env.REACT_APP_API_HOST ?? 'http://localhost:8080') : 'https://mixmaster.io';
export const SPOTIFY_TOKEN = 'spotify_token';