import React from 'react';
import { BrowserRouter as Router, Route, RouteComponentProps, Switch } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import SpotifyAuthorization from './SpotifyAuthorization';
import Start from './Start';
import PlaylistSelect from './PlaylistSelect';

export default class App extends React.Component<Props, State> {
  componentWillMount() {
    this.setState({ spotifyApi: new SpotifyApi() });
  }

  render() {
    const { spotifyApi } = this.state;

    return (
      <Router>
        <Switch>
          <Route exact path="/" render={(props: RouteComponentProps<any>) => (<Start {...props} spotifyApi={spotifyApi} />)} />
          <Route exact path="/spotify" render={(props: RouteComponentProps<any>) => (<SpotifyAuthorization {...props} spotifyApi={spotifyApi} />)} />
          <Route path="/spotify/playlistSelect" render={(props: RouteComponentProps<any>) => (<PlaylistSelect {...props} spotifyApi={spotifyApi} />)} />
        </Switch>
      </Router>
    );
  }
}

type Props = {};

type State = {
  spotifyApi: SpotifyApi;
};