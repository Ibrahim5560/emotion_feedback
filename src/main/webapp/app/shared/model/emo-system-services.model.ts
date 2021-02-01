import { IEmoMessages } from 'app/shared/model/emo-messages.model';

export interface IEmoSystemServices {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number;
  emoSystemServicesMessages?: IEmoMessages[];
  emoSystemId?: number;
}

export const defaultValue: Readonly<IEmoSystemServices> = {};
