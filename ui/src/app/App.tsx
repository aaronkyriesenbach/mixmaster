import React from 'react';
import { BrowserRouter as Router, Route, RouteComponentProps, Switch } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import Authorization from './Authorization';
import Main from './Main';

export default class App extends React.Component<any, State> {
  componentWillMount() {
    this.setState({ spotifyApi: new SpotifyApi() });
  }

  render() {
    const { spotifyApi } = this.state;

    return (
      <Router>
        <Switch>
          <Route path="/spotify/auth" render={(props: RouteComponentProps<any>) => (<Authorization {...props} spotifyApi={spotifyApi} />)} />
          <Route render={(props: RouteComponentProps<any>) => (<Main {...props} spotifyApi={spotifyApi} />)} />
        </Switch>
      </Router>
    );
  }
}

type State = {
  spotifyApi: SpotifyApi;
};