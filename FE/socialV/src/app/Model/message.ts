import {Conversation} from "./conversation";
import {Users} from "./Users";

export interface Messages {
  id?: number
  conversation?: Conversation
  users?: Users
  textAt?: Date
  content?: string
}
