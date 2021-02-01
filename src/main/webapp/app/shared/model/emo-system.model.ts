import { IEmoMessages } from 'app/shared/model/emo-messages.model';
import { IEmoSystemServices } from 'app/shared/model/emo-system-services.model';

export interface IEmoSystem {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number;
  emoSystemMessages?: IEmoMessages[];
  emoSystemServices?: IEmoSystemServices[];
}

export const defaultValue: Readonly<IEmoSystem> = {};
