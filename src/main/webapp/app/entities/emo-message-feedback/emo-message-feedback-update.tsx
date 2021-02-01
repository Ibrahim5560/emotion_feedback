import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './emo-message-feedback.reducer';
import { IEmoMessageFeedback } from 'app/shared/model/emo-message-feedback.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmoMessageFeedbackUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoMessageFeedbackUpdate = (props: IEmoMessageFeedbackUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emoMessageFeedbackEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/emo-message-feedback' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...emoMessageFeedbackEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emotionFeedbackApp.emoMessageFeedback.home.createOrEditLabel">
            <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.home.createOrEditLabel">
              Create or edit a EmoMessageFeedback
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emoMessageFeedbackEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emo-message-feedback-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="emo-message-feedback-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="emoSystemIdLabel" for="emo-message-feedback-emoSystemId">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.emoSystemId">Emo System Id</Translate>
                </Label>
                <AvField id="emo-message-feedback-emoSystemId" type="string" className="form-control" name="emoSystemId" />
              </AvGroup>
              <AvGroup>
                <Label id="centerIdLabel" for="emo-message-feedback-centerId">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.centerId">Center Id</Translate>
                </Label>
                <AvField id="emo-message-feedback-centerId" type="string" className="form-control" name="centerId" />
              </AvGroup>
              <AvGroup>
                <Label id="emoSystemServicesIdLabel" for="emo-message-feedback-emoSystemServicesId">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.emoSystemServicesId">Emo System Services Id</Translate>
                </Label>
                <AvField id="emo-message-feedback-emoSystemServicesId" type="string" className="form-control" name="emoSystemServicesId" />
              </AvGroup>
              <AvGroup>
                <Label id="counterLabel" for="emo-message-feedback-counter">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.counter">Counter</Translate>
                </Label>
                <AvField id="emo-message-feedback-counter" type="string" className="form-control" name="counter" />
              </AvGroup>
              <AvGroup>
                <Label id="trsIdLabel" for="emo-message-feedback-trsId">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.trsId">Trs Id</Translate>
                </Label>
                <AvField id="emo-message-feedback-trsId" type="string" className="form-control" name="trsId" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="emo-message-feedback-userId">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.userId">User Id</Translate>
                </Label>
                <AvField id="emo-message-feedback-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="emo-message-feedback-message">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.message">Message</Translate>
                </Label>
                <AvField id="emo-message-feedback-message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="emo-message-feedback-status">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.status">Status</Translate>
                </Label>
                <AvField id="emo-message-feedback-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="feedbackLabel" for="emo-message-feedback-feedback">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.feedback">Feedback</Translate>
                </Label>
                <AvField id="emo-message-feedback-feedback" type="text" name="feedback" />
              </AvGroup>
              <AvGroup>
                <Label id="applicantNameLabel" for="emo-message-feedback-applicantName">
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.applicantName">Applicant Name</Translate>
                </Label>
                <AvField id="emo-message-feedback-applicantName" type="text" name="applicantName" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/emo-message-feedback" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  emoMessageFeedbackEntity: storeState.emoMessageFeedback.entity,
  loading: storeState.emoMessageFeedback.loading,
  updating: storeState.emoMessageFeedback.updating,
  updateSuccess: storeState.emoMessageFeedback.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoMessageFeedbackUpdate);
