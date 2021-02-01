import { IEmoMessages } from 'app/shared/model/emo-messages.model';

export interface IEmoCenter {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number;
  emoCenterMessages?: IEmoMessages[];
}

export const defaultValue: Readonly<IEmoCenter> = {};
