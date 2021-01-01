import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Authorization from './Authorization';
import Main from './Main';

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/spotify/auth" component={Authorization} />
        <Route component={Main} />
      </Switch>
    </Router>
  );
}

export default App;
