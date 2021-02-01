import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmoMessages from './emo-messages';
import EmoMessagesDetail from './emo-messages-detail';
import EmoMessagesUpdate from './emo-messages-update';
import EmoMessagesDeleteDialog from './emo-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmoMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmoMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmoMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmoMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmoMessagesDeleteDialog} />
  </>
);

export default Routes;
