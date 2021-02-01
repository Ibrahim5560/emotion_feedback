import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmoSystemServices from './emo-system-services';
import EmoSystemServicesDetail from './emo-system-services-detail';
import EmoSystemServicesUpdate from './emo-system-services-update';
import EmoSystemServicesDeleteDialog from './emo-system-services-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmoSystemServicesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmoSystemServicesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmoSystemServicesDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmoSystemServices} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmoSystemServicesDeleteDialog} />
  </>
);

export default Routes;
