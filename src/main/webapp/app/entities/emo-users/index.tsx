import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmoUsers from './emo-users';
import EmoUsersDetail from './emo-users-detail';
import EmoUsersUpdate from './emo-users-update';
import EmoUsersDeleteDialog from './emo-users-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmoUsersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmoUsersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmoUsersDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmoUsers} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmoUsersDeleteDialog} />
  </>
);

export default Routes;
