import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emo-messages.reducer';
import { IEmoMessages } from 'app/shared/model/emo-messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmoMessagesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoMessagesDetail = (props: IEmoMessagesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emoMessagesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionFeedbackApp.emoMessages.detail.title">EmoMessages</Translate> [<b>{emoMessagesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="counter">
              <Translate contentKey="emotionFeedbackApp.emoMessages.counter">Counter</Translate>
            </span>
          </dt>
          <dd>{emoMessagesEntity.counter}</dd>
          <dt>
            <span id="trsId">
              <Translate contentKey="emotionFeedbackApp.emoMessages.trsId">Trs Id</Translate>
            </span>
          </dt>
          <dd>{emoMessagesEntity.trsId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="emotionFeedbackApp.emoMessages.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{emoMessagesEntity.userId}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="emotionFeedbackApp.emoMessages.message">Message</Translate>
            </span>
          </dt>
          <dd>{emoMessagesEntity.message}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionFeedbackApp.emoMessages.status">Status</Translate>
            </span>
          </dt>
          <dd>{emoMessagesEntity.status}</dd>
          <dt>
            <span id="applicantName">
              <Translate contentKey="emotionFeedbackApp.emoMessages.applicantName">Applicant Name</Translate>
            </span>
          </dt>
          <dd>{emoMessagesEntity.applicantName}</dd>
          <dt>
            <Translate contentKey="emotionFeedbackApp.emoMessages.emoCenter">Emo Center</Translate>
          </dt>
          <dd>{emoMessagesEntity.emoCenterId ? emoMessagesEntity.emoCenterId : ''}</dd>
          <dt>
            <Translate contentKey="emotionFeedbackApp.emoMessages.emoSystem">Emo System</Translate>
          </dt>
          <dd>{emoMessagesEntity.emoSystemId ? emoMessagesEntity.emoSystemId : ''}</dd>
          <dt>
            <Translate contentKey="emotionFeedbackApp.emoMessages.emoSystemServices">Emo System Services</Translate>
          </dt>
          <dd>{emoMessagesEntity.emoSystemServicesId ? emoMessagesEntity.emoSystemServicesId : ''}</dd>
          <dt>
            <Translate contentKey="emotionFeedbackApp.emoMessages.emoUsers">Emo Users</Translate>
          </dt>
          <dd>{emoMessagesEntity.emoUsersId ? emoMessagesEntity.emoUsersId : ''}</dd>
        </dl>
        <Button tag={Link} to="/emo-messages" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emo-messages/${emoMessagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emoMessages }: IRootState) => ({
  emoMessagesEntity: emoMessages.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoMessagesDetail);
