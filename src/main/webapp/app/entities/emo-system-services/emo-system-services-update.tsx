import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmoSystem } from 'app/shared/model/emo-system.model';
import { getEntities as getEmoSystems } from 'app/entities/emo-system/emo-system.reducer';
import { getEntity, updateEntity, createEntity, reset } from './emo-system-services.reducer';
import { IEmoSystemServices } from 'app/shared/model/emo-system-services.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmoSystemServicesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoSystemServicesUpdate = (props: IEmoSystemServicesUpdateProps) => {
  const [emoSystemId, setEmoSystemId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emoSystemServicesEntity, emoSystems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/emo-system-services' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmoSystems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...emoSystemServicesEntity,
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
          <h2 id="emotionFeedbackApp.emoSystemServices.home.createOrEditLabel">
            <Translate contentKey="emotionFeedbackApp.emoSystemServices.home.createOrEditLabel">
              Create or edit a EmoSystemServices
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emoSystemServicesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emo-system-services-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="emo-system-services-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameArLabel" for="emo-system-services-nameAr">
                  <Translate contentKey="emotionFeedbackApp.emoSystemServices.nameAr">Name Ar</Translate>
                </Label>
                <AvField id="emo-system-services-nameAr" type="text" name="nameAr" />
              </AvGroup>
              <AvGroup>
                <Label id="nameEnLabel" for="emo-system-services-nameEn">
                  <Translate contentKey="emotionFeedbackApp.emoSystemServices.nameEn">Name En</Translate>
                </Label>
                <AvField id="emo-system-services-nameEn" type="text" name="nameEn" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="emo-system-services-code">
                  <Translate contentKey="emotionFeedbackApp.emoSystemServices.code">Code</Translate>
                </Label>
                <AvField id="emo-system-services-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="emo-system-services-status">
                  <Translate contentKey="emotionFeedbackApp.emoSystemServices.status">Status</Translate>
                </Label>
                <AvField id="emo-system-services-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <AvGroup>
                <Label for="emo-system-services-emoSystem">
                  <Translate contentKey="emotionFeedbackApp.emoSystemServices.emoSystem">Emo System</Translate>
                </Label>
                <AvInput id="emo-system-services-emoSystem" type="select" className="form-control" name="emoSystemId">
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
              <Button tag={Link} id="cancel-save" to="/emo-system-services" replace color="info">
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
  emoSystems: storeState.emoSystem.entities,
  emoSystemServicesEntity: storeState.emoSystemServices.entity,
  loading: storeState.emoSystemServices.loading,
  updating: storeState.emoSystemServices.updating,
  updateSuccess: storeState.emoSystemServices.updateSuccess,
});

const mapDispatchToProps = {
  getEmoSystems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoSystemServicesUpdate);
