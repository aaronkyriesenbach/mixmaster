import cookie from 'cookie';
import { Route } from 'react-router-dom';
import { SPOTIFY_TOKEN } from '../constants';

export default function AuthenticatedRoute(props: any) {
    const token = cookie.parse(document.cookie)[SPOTIFY_TOKEN];

    if (token === undefined) {
        window.location.href = "/";

    }
    return <Route {...props} />;
}