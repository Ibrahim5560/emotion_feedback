import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmoUsers, defaultValue } from 'app/shared/model/emo-users.model';

export const ACTION_TYPES = {
  FETCH_EMOUSERS_LIST: 'emoUsers/FETCH_EMOUSERS_LIST',
  FETCH_EMOUSERS: 'emoUsers/FETCH_EMOUSERS',
  CREATE_EMOUSERS: 'emoUsers/CREATE_EMOUSERS',
  UPDATE_EMOUSERS: 'emoUsers/UPDATE_EMOUSERS',
  DELETE_EMOUSERS: 'emoUsers/DELETE_EMOUSERS',
  RESET: 'emoUsers/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmoUsers>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmoUsersState = Readonly<typeof initialState>;

// Reducer

export default (state: EmoUsersState = initialState, action): EmoUsersState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMOUSERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMOUSERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMOUSERS):
    case REQUEST(ACTION_TYPES.UPDATE_EMOUSERS):
    case REQUEST(ACTION_TYPES.DELETE_EMOUSERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMOUSERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMOUSERS):
    case FAILURE(ACTION_TYPES.CREATE_EMOUSERS):
    case FAILURE(ACTION_TYPES.UPDATE_EMOUSERS):
    case FAILURE(ACTION_TYPES.DELETE_EMOUSERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOUSERS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOUSERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMOUSERS):
    case SUCCESS(ACTION_TYPES.UPDATE_EMOUSERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMOUSERS):
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

const apiUrl = 'api/emo-users';

// Actions

export const getEntities: ICrudGetAllAction<IEmoUsers> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMOUSERS_LIST,
    payload: axios.get<IEmoUsers>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmoUsers> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMOUSERS,
    payload: axios.get<IEmoUsers>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmoUsers> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMOUSERS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmoUsers> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMOUSERS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmoUsers> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMOUSERS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
