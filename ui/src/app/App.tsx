import React from 'react';
import { BrowserRouter as Router, Route, RouteComponentProps, Switch } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import AuthenticatedRoute from './AuthenticatedRoute';
import PlaylistSelect from './playlist/PlaylistSelect';
import SpotifyAuthorization from './SpotifyAuthorization';
import Start from './Start';
import Clean, { Props as CleanProps } from './Clean';

export default class App extends React.Component<Props, State> {
  componentWillMount() {
    this.setState({ spotifyApi: new SpotifyApi() });
  }

  render() {
    const { spotifyApi } = this.state;

    return (
      <Router>
        <Switch>
          <Route
            exact path="/"
            render={props =>
              <Start {...props} spotifyApi={spotifyApi} />}
          />
          <Route
            exact path="/spotify"
            render={props => <SpotifyAuthorization {...props} spotifyApi={spotifyApi} />}
          />
          <AuthenticatedRoute
            path="/spotify/playlistSelect"
            render={(props: RouteComponentProps) =>
              <PlaylistSelect {...props} spotifyApi={spotifyApi} />}
          />
          <AuthenticatedRoute
            path="/spotify/:id/clean"
            render={(props: CleanProps) =>
              <Clean {...props} spotifyApi={spotifyApi} />}
          />
        </Switch>
      </Router>
    );
  }
}

type Props = {};

type State = {
  spotifyApi: SpotifyApi;
};