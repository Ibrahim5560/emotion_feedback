import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmoCenter } from 'app/shared/model/emo-center.model';
import { getEntities as getEmoCenters } from 'app/entities/emo-center/emo-center.reducer';
import { IEmoSystem } from 'app/shared/model/emo-system.model';
import { getEntities as getEmoSystems } from 'app/entities/emo-system/emo-system.reducer';
import { IEmoSystemServices } from 'app/shared/model/emo-system-services.model';
import { getEntities as getEmoSystemServices } from 'app/entities/emo-system-services/emo-system-services.reducer';
import { IEmoUsers } from 'app/shared/model/emo-users.model';
import { getEntities as getEmoUsers } from 'app/entities/emo-users/emo-users.reducer';
import { getEntity, updateEntity, createEntity, reset } from './emo-messages.reducer';
import { IEmoMessages } from 'app/shared/model/emo-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmoMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoMessagesUpdate = (props: IEmoMessagesUpdateProps) => {
  const [emoCenterId, setEmoCenterId] = useState('0');
  const [emoSystemId, setEmoSystemId] = useState('0');
  const [emoSystemServicesId, setEmoSystemServicesId] = useState('0');
  const [emoUsersId, setEmoUsersId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emoMessagesEntity, emoCenters, emoSystems, emoSystemServices, emoUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/emo-messages' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmoCenters();
    props.getEmoSystems();
    props.getEmoSystemServices();
    props.getEmoUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...emoMessagesEntity,
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
          <h2 id="emotionFeedbackApp.emoMessages.home.createOrEditLabel">
            <Translate contentKey="emotionFeedbackApp.emoMessages.home.createOrEditLabel">Create or edit a EmoMessages</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emoMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emo-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="emo-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="counterLabel" for="emo-messages-counter">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.counter">Counter</Translate>
                </Label>
                <AvField id="emo-messages-counter" type="string" className="form-control" name="counter" />
              </AvGroup>
              <AvGroup>
                <Label id="trsIdLabel" for="emo-messages-trsId">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.trsId">Trs Id</Translate>
                </Label>
                <AvField id="emo-messages-trsId" type="string" className="form-control" name="trsId" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="emo-messages-userId">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.userId">User Id</Translate>
                </Label>
                <AvField id="emo-messages-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="emo-messages-message">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.message">Message</Translate>
                </Label>
                <AvField id="emo-messages-message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="emo-messages-status">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.status">Status</Translate>
                </Label>
                <AvField id="emo-messages-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="applicantNameLabel" for="emo-messages-applicantName">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.applicantName">Applicant Name</Translate>
                </Label>
                <AvField id="emo-messages-applicantName" type="text" name="applicantName" />
              </AvGroup>
              <AvGroup>
                <Label for="emo-messages-emoCenter">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoCenter">Emo Center</Translate>
                </Label>
                <AvInput id="emo-messages-emoCenter" type="select" className="form-control" name="emoCenterId">
                  <option value="" key="0" />
                  {emoCenters
                    ? emoCenters.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emo-messages-emoSystem">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoSystem">Emo System</Translate>
                </Label>
                <AvInput id="emo-messages-emoSystem" type="select" className="form-control" name="emoSystemId">
                  <option value="" key="0" />
                  {emoSystems
                    ? emoSystems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emo-messages-emoSystemServices">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoSystemServices">Emo System Services</Translate>
                </Label>
                <AvInput id="emo-messages-emoSystemServices" type="select" className="form-control" name="emoSystemServicesId">
                  <option value="" key="0" />
                  {emoSystemServices
                    ? emoSystemServices.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="emo-messages-emoUsers">
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoUsers">Emo Users</Translate>
                </Label>
                <AvInput id="emo-messages-emoUsers" type="select" className="form-control" name="emoUsersId">
                  <option value="" key="0" />
                  {emoUsers
                    ? emoUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/emo-messages" replace color="info">
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
  emoCenters: storeState.emoCenter.entities,
  emoSystems: storeState.emoSystem.entities,
  emoSystemServices: storeState.emoSystemServices.entities,
  emoUsers: storeState.emoUsers.entities,
  emoMessagesEntity: storeState.emoMessages.entity,
  loading: storeState.emoMessages.loading,
  updating: storeState.emoMessages.updating,
  updateSuccess: storeState.emoMessages.updateSuccess,
});

const mapDispatchToProps = {
  getEmoCenters,
  getEmoSystems,
  getEmoSystemServices,
  getEmoUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoMessagesUpdate);
