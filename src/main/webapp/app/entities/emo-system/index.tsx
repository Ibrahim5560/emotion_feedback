import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmoSystem from './emo-system';
import EmoSystemDetail from './emo-system-detail';
import EmoSystemUpdate from './emo-system-update';
import EmoSystemDeleteDialog from './emo-system-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmoSystemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmoSystemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmoSystemDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmoSystem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmoSystemDeleteDialog} />
  </>
);

export default Routes;
