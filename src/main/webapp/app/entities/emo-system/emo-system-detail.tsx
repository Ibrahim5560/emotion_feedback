import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emo-system.reducer';
import { IEmoSystem } from 'app/shared/model/emo-system.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmoSystemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoSystemDetail = (props: IEmoSystemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emoSystemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionFeedbackApp.emoSystem.detail.title">EmoSystem</Translate> [<b>{emoSystemEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionFeedbackApp.emoSystem.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{emoSystemEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionFeedbackApp.emoSystem.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{emoSystemEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionFeedbackApp.emoSystem.code">Code</Translate>
            </span>
          </dt>
          <dd>{emoSystemEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionFeedbackApp.emoSystem.status">Status</Translate>
            </span>
          </dt>
          <dd>{emoSystemEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/emo-system" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emo-system/${emoSystemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emoSystem }: IRootState) => ({
  emoSystemEntity: emoSystem.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoSystemDetail);
