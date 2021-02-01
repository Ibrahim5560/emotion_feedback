import { IEmoMessages } from 'app/shared/model/emo-messages.model';

export interface IEmoUsers {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number;
  emoUsersMessages?: IEmoMessages[];
}

export const defaultValue: Readonly<IEmoUsers> = {};
