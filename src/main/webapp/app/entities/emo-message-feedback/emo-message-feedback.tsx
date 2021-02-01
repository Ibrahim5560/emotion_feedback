import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './emo-message-feedback.reducer';
import { IEmoMessageFeedback } from 'app/shared/model/emo-message-feedback.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEmoMessageFeedbackProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmoMessageFeedback = (props: IEmoMessageFeedbackProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { emoMessageFeedbackList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="emo-message-feedback-heading">
        <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.home.title">Emo Message Feedbacks</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.home.createLabel">Create new Emo Message Feedback</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {emoMessageFeedbackList && emoMessageFeedbackList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emoSystemId')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.emoSystemId">Emo System Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('centerId')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.centerId">Center Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emoSystemServicesId')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.emoSystemServicesId">Emo System Services Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('counter')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.counter">Counter</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('trsId')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.trsId">Trs Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userId')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.userId">User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('message')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('feedback')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.feedback">Feedback</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('applicantName')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.applicantName">Applicant Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {emoMessageFeedbackList.map((emoMessageFeedback, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${emoMessageFeedback.id}`} color="link" size="sm">
                      {emoMessageFeedback.id}
                    </Button>
                  </td>
                  <td>{emoMessageFeedback.emoSystemId}</td>
                  <td>{emoMessageFeedback.centerId}</td>
                  <td>{emoMessageFeedback.emoSystemServicesId}</td>
                  <td>{emoMessageFeedback.counter}</td>
                  <td>{emoMessageFeedback.trsId}</td>
                  <td>{emoMessageFeedback.userId}</td>
                  <td>{emoMessageFeedback.message}</td>
                  <td>{emoMessageFeedback.status}</td>
                  <td>{emoMessageFeedback.feedback}</td>
                  <td>{emoMessageFeedback.applicantName}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${emoMessageFeedback.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${emoMessageFeedback.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${emoMessageFeedback.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.home.notFound">No Emo Message Feedbacks found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={emoMessageFeedbackList && emoMessageFeedbackList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ emoMessageFeedback }: IRootState) => ({
  emoMessageFeedbackList: emoMessageFeedback.entities,
  loading: emoMessageFeedback.loading,
  totalItems: emoMessageFeedback.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoMessageFeedback);
