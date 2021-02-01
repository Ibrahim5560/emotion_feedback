import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emo-system-services.reducer';
import { IEmoSystemServices } from 'app/shared/model/emo-system-services.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmoSystemServicesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoSystemServicesDetail = (props: IEmoSystemServicesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emoSystemServicesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionFeedbackApp.emoSystemServices.detail.title">EmoSystemServices</Translate> [
          <b>{emoSystemServicesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionFeedbackApp.emoSystemServices.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{emoSystemServicesEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionFeedbackApp.emoSystemServices.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{emoSystemServicesEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionFeedbackApp.emoSystemServices.code">Code</Translate>
            </span>
          </dt>
          <dd>{emoSystemServicesEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionFeedbackApp.emoSystemServices.status">Status</Translate>
            </span>
          </dt>
          <dd>{emoSystemServicesEntity.status}</dd>
          <dt>
            <Translate contentKey="emotionFeedbackApp.emoSystemServices.emoSystem">Emo System</Translate>
          </dt>
          <dd>{emoSystemServicesEntity.emoSystemId ? emoSystemServicesEntity.emoSystemId : ''}</dd>
        </dl>
        <Button tag={Link} to="/emo-system-services" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emo-system-services/${emoSystemServicesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emoSystemServices }: IRootState) => ({
  emoSystemServicesEntity: emoSystemServices.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoSystemServicesDetail);
