import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emo-center.reducer';
import { IEmoCenter } from 'app/shared/model/emo-center.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmoCenterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoCenterDetail = (props: IEmoCenterDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emoCenterEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionFeedbackApp.emoCenter.detail.title">EmoCenter</Translate> [<b>{emoCenterEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionFeedbackApp.emoCenter.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{emoCenterEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionFeedbackApp.emoCenter.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{emoCenterEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionFeedbackApp.emoCenter.code">Code</Translate>
            </span>
          </dt>
          <dd>{emoCenterEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionFeedbackApp.emoCenter.status">Status</Translate>
            </span>
          </dt>
          <dd>{emoCenterEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/emo-center" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emo-center/${emoCenterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emoCenter }: IRootState) => ({
  emoCenterEntity: emoCenter.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoCenterDetail);
