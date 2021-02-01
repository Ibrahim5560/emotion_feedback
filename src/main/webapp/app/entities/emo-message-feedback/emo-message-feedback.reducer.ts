import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmoMessageFeedback, defaultValue } from 'app/shared/model/emo-message-feedback.model';

export const ACTION_TYPES = {
  FETCH_EMOMESSAGEFEEDBACK_LIST: 'emoMessageFeedback/FETCH_EMOMESSAGEFEEDBACK_LIST',
  FETCH_EMOMESSAGEFEEDBACK: 'emoMessageFeedback/FETCH_EMOMESSAGEFEEDBACK',
  CREATE_EMOMESSAGEFEEDBACK: 'emoMessageFeedback/CREATE_EMOMESSAGEFEEDBACK',
  UPDATE_EMOMESSAGEFEEDBACK: 'emoMessageFeedback/UPDATE_EMOMESSAGEFEEDBACK',
  DELETE_EMOMESSAGEFEEDBACK: 'emoMessageFeedback/DELETE_EMOMESSAGEFEEDBACK',
  RESET: 'emoMessageFeedback/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmoMessageFeedback>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmoMessageFeedbackState = Readonly<typeof initialState>;

// Reducer

export default (state: EmoMessageFeedbackState = initialState, action): EmoMessageFeedbackState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMOMESSAGEFEEDBACK):
    case REQUEST(ACTION_TYPES.UPDATE_EMOMESSAGEFEEDBACK):
    case REQUEST(ACTION_TYPES.DELETE_EMOMESSAGEFEEDBACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK):
    case FAILURE(ACTION_TYPES.CREATE_EMOMESSAGEFEEDBACK):
    case FAILURE(ACTION_TYPES.UPDATE_EMOMESSAGEFEEDBACK):
    case FAILURE(ACTION_TYPES.DELETE_EMOMESSAGEFEEDBACK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMOMESSAGEFEEDBACK):
    case SUCCESS(ACTION_TYPES.UPDATE_EMOMESSAGEFEEDBACK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMOMESSAGEFEEDBACK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/emo-message-feedbacks';

// Actions

export const getEntities: ICrudGetAllAction<IEmoMessageFeedback> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK_LIST,
    payload: axios.get<IEmoMessageFeedback>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmoMessageFeedback> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMOMESSAGEFEEDBACK,
    payload: axios.get<IEmoMessageFeedback>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmoMessageFeedback> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMOMESSAGEFEEDBACK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmoMessageFeedback> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMOMESSAGEFEEDBACK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmoMessageFeedback> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMOMESSAGEFEEDBACK,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
