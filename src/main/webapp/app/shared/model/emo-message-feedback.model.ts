export interface IEmoMessageFeedback {
  id?: number;
  emoSystemId?: number;
  centerId?: number;
  emoSystemServicesId?: number;
  counter?: number;
  trsId?: number;
  userId?: number;
  message?: string;
  status?: number;
  feedback?: string;
  applicantName?: string;
}

export const defaultValue: Readonly<IEmoMessageFeedback> = {};
