import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emo-message-feedback.reducer';
import { IEmoMessageFeedback } from 'app/shared/model/emo-message-feedback.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmoMessageFeedbackDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoMessageFeedbackDetail = (props: IEmoMessageFeedbackDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emoMessageFeedbackEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.detail.title">EmoMessageFeedback</Translate> [
          <b>{emoMessageFeedbackEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="emoSystemId">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.emoSystemId">Emo System Id</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.emoSystemId}</dd>
          <dt>
            <span id="centerId">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.centerId">Center Id</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.centerId}</dd>
          <dt>
            <span id="emoSystemServicesId">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.emoSystemServicesId">Emo System Services Id</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.emoSystemServicesId}</dd>
          <dt>
            <span id="counter">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.counter">Counter</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.counter}</dd>
          <dt>
            <span id="trsId">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.trsId">Trs Id</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.trsId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.userId}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.message">Message</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.message}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.status">Status</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.status}</dd>
          <dt>
            <span id="feedback">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.feedback">Feedback</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.feedback}</dd>
          <dt>
            <span id="applicantName">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.applicantName">Applicant Name</Translate>
            </span>
          </dt>
          <dd>{emoMessageFeedbackEntity.applicantName}</dd>
        </dl>
        <Button tag={Link} to="/emo-message-feedback" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emo-message-feedback/${emoMessageFeedbackEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emoMessageFeedback }: IRootState) => ({
  emoMessageFeedbackEntity: emoMessageFeedback.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoMessageFeedbackDetail);
