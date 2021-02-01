import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmoMessages, defaultValue } from 'app/shared/model/emo-messages.model';

export const ACTION_TYPES = {
  FETCH_EMOMESSAGES_LIST: 'emoMessages/FETCH_EMOMESSAGES_LIST',
  FETCH_EMOMESSAGES: 'emoMessages/FETCH_EMOMESSAGES',
  CREATE_EMOMESSAGES: 'emoMessages/CREATE_EMOMESSAGES',
  UPDATE_EMOMESSAGES: 'emoMessages/UPDATE_EMOMESSAGES',
  DELETE_EMOMESSAGES: 'emoMessages/DELETE_EMOMESSAGES',
  RESET: 'emoMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmoMessages>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmoMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: EmoMessagesState = initialState, action): EmoMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMOMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMOMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMOMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_EMOMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_EMOMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMOMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMOMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_EMOMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_EMOMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_EMOMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMOMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_EMOMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMOMESSAGES):
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

const apiUrl = 'api/emo-messages';

// Actions

export const getEntities: ICrudGetAllAction<IEmoMessages> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMOMESSAGES_LIST,
    payload: axios.get<IEmoMessages>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmoMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMOMESSAGES,
    payload: axios.get<IEmoMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmoMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMOMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmoMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMOMESSAGES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmoMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMOMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
