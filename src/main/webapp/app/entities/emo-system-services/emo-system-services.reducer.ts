import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmoSystemServices, defaultValue } from 'app/shared/model/emo-system-services.model';

export const ACTION_TYPES = {
  FETCH_EMOSYSTEMSERVICES_LIST: 'emoSystemServices/FETCH_EMOSYSTEMSERVICES_LIST',
  FETCH_EMOSYSTEMSERVICES: 'emoSystemServices/FETCH_EMOSYSTEMSERVICES',
  CREATE_EMOSYSTEMSERVICES: 'emoSystemServices/CREATE_EMOSYSTEMSERVICES',
  UPDATE_EMOSYSTEMSERVICES: 'emoSystemServices/UPDATE_EMOSYSTEMSERVICES',
  DELETE_EMOSYSTEMSERVICES: 'emoSystemServices/DELETE_EMOSYSTEMSERVICES',
  RESET: 'emoSystemServices/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmoSystemServices>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmoSystemServicesState = Readonly<typeof initialState>;

// Reducer

export default (state: EmoSystemServicesState = initialState, action): EmoSystemServicesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMOSYSTEMSERVICES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMOSYSTEMSERVICES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMOSYSTEMSERVICES):
    case REQUEST(ACTION_TYPES.UPDATE_EMOSYSTEMSERVICES):
    case REQUEST(ACTION_TYPES.DELETE_EMOSYSTEMSERVICES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMOSYSTEMSERVICES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMOSYSTEMSERVICES):
    case FAILURE(ACTION_TYPES.CREATE_EMOSYSTEMSERVICES):
    case FAILURE(ACTION_TYPES.UPDATE_EMOSYSTEMSERVICES):
    case FAILURE(ACTION_TYPES.DELETE_EMOSYSTEMSERVICES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOSYSTEMSERVICES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMOSYSTEMSERVICES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMOSYSTEMSERVICES):
    case SUCCESS(ACTION_TYPES.UPDATE_EMOSYSTEMSERVICES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMOSYSTEMSERVICES):
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

const apiUrl = 'api/emo-system-services';

// Actions

export const getEntities: ICrudGetAllAction<IEmoSystemServices> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMOSYSTEMSERVICES_LIST,
    payload: axios.get<IEmoSystemServices>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmoSystemServices> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMOSYSTEMSERVICES,
    payload: axios.get<IEmoSystemServices>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmoSystemServices> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMOSYSTEMSERVICES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmoSystemServices> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMOSYSTEMSERVICES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmoSystemServices> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMOSYSTEMSERVICES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
