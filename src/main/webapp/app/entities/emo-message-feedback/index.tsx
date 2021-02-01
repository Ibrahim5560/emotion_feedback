import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmoMessageFeedback from './emo-message-feedback';
import EmoMessageFeedbackDetail from './emo-message-feedback-detail';
import EmoMessageFeedbackUpdate from './emo-message-feedback-update';
import EmoMessageFeedbackDeleteDialog from './emo-message-feedback-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmoMessageFeedbackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmoMessageFeedbackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmoMessageFeedbackDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmoMessageFeedback} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmoMessageFeedbackDeleteDialog} />
  </>
);

export default Routes;
