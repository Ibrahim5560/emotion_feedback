import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmoCenter from './emo-center';
import EmoCenterDetail from './emo-center-detail';
import EmoCenterUpdate from './emo-center-update';
import EmoCenterDeleteDialog from './emo-center-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmoCenterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmoCenterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmoCenterDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmoCenter} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmoCenterDeleteDialog} />
  </>
);

export default Routes;
