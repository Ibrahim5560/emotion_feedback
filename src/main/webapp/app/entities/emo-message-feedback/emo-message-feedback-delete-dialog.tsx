import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IEmoMessageFeedback } from 'app/shared/model/emo-message-feedback.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './emo-message-feedback.reducer';

export interface IEmoMessageFeedbackDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmoMessageFeedbackDeleteDialog = (props: IEmoMessageFeedbackDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/emo-message-feedback' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.emoMessageFeedbackEntity.id);
  };

  const { emoMessageFeedbackEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="emotionFeedbackApp.emoMessageFeedback.delete.question">
        <Translate contentKey="emotionFeedbackApp.emoMessageFeedback.delete.question" interpolate={{ id: emoMessageFeedbackEntity.id }}>
          Are you sure you want to delete this EmoMessageFeedback?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-emoMessageFeedback" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ emoMessageFeedback }: IRootState) => ({
  emoMessageFeedbackEntity: emoMessageFeedback.entity,
  updateSuccess: emoMessageFeedback.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmoMessageFeedbackDeleteDialog);
