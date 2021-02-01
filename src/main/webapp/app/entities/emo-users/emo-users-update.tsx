import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './emo-users.reducer';
import { IEmoUsers } from 'app/shared/model/emo-users.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmoUsersUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoUsersUpdate = (props: IEmoUsersUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emoUsersEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/emo-users' + props.location.search);
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
        ...emoUsersEntity,
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
          <h2 id="emotionFeedbackApp.emoUsers.home.createOrEditLabel">
            <Translate contentKey="emotionFeedbackApp.emoUsers.home.createOrEditLabel">Create or edit a EmoUsers</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emoUsersEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emo-users-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="emo-users-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameArLabel" for="emo-users-nameAr">
                  <Translate contentKey="emotionFeedbackApp.emoUsers.nameAr">Name Ar</Translate>
                </Label>
                <AvField id="emo-users-nameAr" type="text" name="nameAr" />
              </AvGroup>
              <AvGroup>
                <Label id="nameEnLabel" for="emo-users-nameEn">
                  <Translate contentKey="emotionFeedbackApp.emoUsers.nameEn">Name En</Translate>
                </Label>
                <AvField id="emo-users-nameEn" type="text" name="nameEn" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="emo-users-code">
                  <Translate contentKey="emotionFeedbackApp.emoUsers.code">Code</Translate>
                </Label>
                <AvField id="emo-users-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="emo-users-status">
                  <Translate contentKey="emotionFeedbackApp.emoUsers.status">Status</Translate>
                </Label>
                <AvField id="emo-users-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/emo-users" replace color="info">
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
  emoUsersEntity: storeState.emoUsers.entity,
  loading: storeState.emoUsers.loading,
  updating: storeState.emoUsers.updating,
  updateSuccess: storeState.emoUsers.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoUsersUpdate);
