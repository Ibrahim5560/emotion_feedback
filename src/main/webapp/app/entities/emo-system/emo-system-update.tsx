import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './emo-system.reducer';
import { IEmoSystem } from 'app/shared/model/emo-system.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmoSystemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoSystemUpdate = (props: IEmoSystemUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emoSystemEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/emo-system' + props.location.search);
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
        ...emoSystemEntity,
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
          <h2 id="emotionFeedbackApp.emoSystem.home.createOrEditLabel">
            <Translate contentKey="emotionFeedbackApp.emoSystem.home.createOrEditLabel">Create or edit a EmoSystem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emoSystemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emo-system-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="emo-system-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameArLabel" for="emo-system-nameAr">
                  <Translate contentKey="emotionFeedbackApp.emoSystem.nameAr">Name Ar</Translate>
                </Label>
                <AvField id="emo-system-nameAr" type="text" name="nameAr" />
              </AvGroup>
              <AvGroup>
                <Label id="nameEnLabel" for="emo-system-nameEn">
                  <Translate contentKey="emotionFeedbackApp.emoSystem.nameEn">Name En</Translate>
                </Label>
                <AvField id="emo-system-nameEn" type="text" name="nameEn" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="emo-system-code">
                  <Translate contentKey="emotionFeedbackApp.emoSystem.code">Code</Translate>
                </Label>
                <AvField id="emo-system-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="emo-system-status">
                  <Translate contentKey="emotionFeedbackApp.emoSystem.status">Status</Translate>
                </Label>
                <AvField id="emo-system-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/emo-system" replace color="info">
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
  emoSystemEntity: storeState.emoSystem.entity,
  loading: storeState.emoSystem.loading,
  updating: storeState.emoSystem.updating,
  updateSuccess: storeState.emoSystem.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoSystemUpdate);
