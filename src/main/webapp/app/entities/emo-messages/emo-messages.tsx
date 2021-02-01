import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './emo-messages.reducer';
import { IEmoMessages } from 'app/shared/model/emo-messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEmoMessagesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmoMessages = (props: IEmoMessagesProps) => {
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

  const { emoMessagesList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="emo-messages-heading">
        <Translate contentKey="emotionFeedbackApp.emoMessages.home.title">Emo Messages</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emotionFeedbackApp.emoMessages.home.createLabel">Create new Emo Messages</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {emoMessagesList && emoMessagesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('counter')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.counter">Counter</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('trsId')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.trsId">Trs Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userId')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.userId">User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('message')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('applicantName')}>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.applicantName">Applicant Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoCenter">Emo Center</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoSystem">Emo System</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoSystemServices">Emo System Services</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="emotionFeedbackApp.emoMessages.emoUsers">Emo Users</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {emoMessagesList.map((emoMessages, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${emoMessages.id}`} color="link" size="sm">
                      {emoMessages.id}
                    </Button>
                  </td>
                  <td>{emoMessages.counter}</td>
                  <td>{emoMessages.trsId}</td>
                  <td>{emoMessages.userId}</td>
                  <td>{emoMessages.message}</td>
                  <td>{emoMessages.status}</td>
                  <td>{emoMessages.applicantName}</td>
                  <td>
                    {emoMessages.emoCenterId ? <Link to={`emo-center/${emoMessages.emoCenterId}`}>{emoMessages.emoCenterId}</Link> : ''}
                  </td>
                  <td>
                    {emoMessages.emoSystemId ? <Link to={`emo-system/${emoMessages.emoSystemId}`}>{emoMessages.emoSystemId}</Link> : ''}
                  </td>
                  <td>
                    {emoMessages.emoSystemServicesId ? (
                      <Link to={`emo-system-services/${emoMessages.emoSystemServicesId}`}>{emoMessages.emoSystemServicesId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{emoMessages.emoUsersId ? <Link to={`emo-users/${emoMessages.emoUsersId}`}>{emoMessages.emoUsersId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${emoMessages.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${emoMessages.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${emoMessages.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="emotionFeedbackApp.emoMessages.home.notFound">No Emo Messages found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={emoMessagesList && emoMessagesList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ emoMessages }: IRootState) => ({
  emoMessagesList: emoMessages.entities,
  loading: emoMessages.loading,
  totalItems: emoMessages.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoMessages);
