import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmoSystem from './emo-system';
import EmoSystemServices from './emo-system-services';
import EmoCenter from './emo-center';
import EmoUsers from './emo-users';
import EmoMessages from './emo-messages';
import EmoMessageFeedback from './emo-message-feedback';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}emo-system`} component={EmoSystem} />
      <ErrorBoundaryRoute path={`${match.url}emo-system-services`} component={EmoSystemServices} />
      <ErrorBoundaryRoute path={`${match.url}emo-center`} component={EmoCenter} />
      <ErrorBoundaryRoute path={`${match.url}emo-users`} component={EmoUsers} />
      <ErrorBoundaryRoute path={`${match.url}emo-messages`} component={EmoMessages} />
      <ErrorBoundaryRoute path={`${match.url}emo-message-feedback`} component={EmoMessageFeedback} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
