import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emo-users.reducer';
import { IEmoUsers } from 'app/shared/model/emo-users.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmoUsersDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoUsersDetail = (props: IEmoUsersDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emoUsersEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionFeedbackApp.emoUsers.detail.title">EmoUsers</Translate> [<b>{emoUsersEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionFeedbackApp.emoUsers.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{emoUsersEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionFeedbackApp.emoUsers.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{emoUsersEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionFeedbackApp.emoUsers.code">Code</Translate>
            </span>
          </dt>
          <dd>{emoUsersEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionFeedbackApp.emoUsers.status">Status</Translate>
            </span>
          </dt>
          <dd>{emoUsersEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/emo-users" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emo-users/${emoUsersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emoUsers }: IRootState) => ({
  emoUsersEntity: emoUsers.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoUsersDetail);
