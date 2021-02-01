export interface IEmoMessages {
  id?: number;
  counter?: number;
  trsId?: number;
  userId?: number;
  message?: string;
  status?: number;
  applicantName?: string;
  emoCenterId?: number;
  emoSystemId?: number;
  emoSystemServicesId?: number;
  emoUsersId?: number;
}

export const defaultValue: Readonly<IEmoMessages> = {};
