import { faMusic } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { Container, Navbar, NavbarBrand } from 'react-bootstrap';
import { BrowserRouter as Router, Route, RouteComponentProps, Switch } from 'react-router-dom';
import SpotifyApi from '../api/SpotifyApi';
import AuthenticatedRoute from './AuthenticatedRoute';
import Clean, { Props as CleanProps } from './clean/Clean';
import Loading from './loading/Loading';
import PlaylistSelect from './playlist/PlaylistSelect';
import PostClean from './post-clean/PostClean';
import SpotifyAuthorization from './SpotifyAuthorization';
import Start from './Start';
import './_styles.css';

export default class App extends React.Component<Props, State> {
  componentDidMount() {
    this.setState({ spotifyApi: new SpotifyApi() });
  }

  render() {
    const { spotifyApi } = this.state || {};

    if (!spotifyApi) {
      return <Loading />;
    }

    return (
      <div>
        <Navbar>
          <NavbarBrand href='/'>
            <FontAwesomeIcon icon={faMusic} /> MixMaster
          </NavbarBrand>
        </Navbar>
        <Container fluid className='vh-100'>
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
              <AuthenticatedRoute
                path="/spotify/postClean"
                render={(props: RouteComponentProps) =>
                  <PostClean {...props} spotifyApi={spotifyApi} />}
              />
            </Switch>
          </Router>
        </Container>
      </div>
    );
  }
}

type Props = {};

type State = {
  spotifyApi: SpotifyApi;
};