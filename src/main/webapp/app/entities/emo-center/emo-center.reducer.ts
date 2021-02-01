import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmoCenter, defaultValue } from 'app/shared/model/emo-center.model';

export const ACTION_TYPES = {
  FETCH_EMOCENTER_LIST: 'emoCenter/FETCH_EMOCENTER_LIST',
  FETCH_EMOCENTER: 'emoCenter/FETCH_EMOCENTER',
  CREATE_EMOCENTER: 'emoCenter/CREATE_EMOCENTER',
  UPDATE_EMOCENTER: 'emoCenter/UPDATE_EMOCENTER',
  DELETE_EMOCENTER: 'emoCenter/DELETE_EMOCENTER',
  RESET: 'emoCenter/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmoCenter>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmoCenterState = Readonly<typeof initialState>;

// Reducer

export default (state: EmoCenterState = initialState, action): EmoCenterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMOCENTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMOCENTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMOCENTER):
    case REQUEST(ACTION_TYPES.UPDATE_EMOCENTER):
    case REQUEST(ACTION_TYPES.DELETE_EMOCENTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMOCENTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMOCENTER):
    case FAILURE(ACTION_TYPES.CREATE_EMOCENTER):
    case FAILURE(ACTION_TYPES.UPDATE_EMOCENTER):
    case FAILURE(ACTION_TYPES.DELETE_EMOCENTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOCENTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOCENTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMOCENTER):
    case SUCCESS(ACTION_TYPES.UPDATE_EMOCENTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMOCENTER):
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

const apiUrl = 'api/emo-centers';

// Actions

export const getEntities: ICrudGetAllAction<IEmoCenter> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMOCENTER_LIST,
    payload: axios.get<IEmoCenter>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmoCenter> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMOCENTER,
    payload: axios.get<IEmoCenter>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmoCenter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMOCENTER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmoCenter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMOCENTER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmoCenter> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMOCENTER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
