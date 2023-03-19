import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Users} from "../Model/Users";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Conversation} from "../Model/conversation";
import {Messages} from "../Model/message";

const apiUrl = environment.apiUrl

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) {
  }

  getAllPersonalConversation(user: Users): Observable<any> {
    return this.http.get<Conversation[]>(apiUrl + `/chat/room/${user.id}`);
  }

  getAllGroupConversation(user: Users): Observable<any> {
    return this.http.get<Conversation[]>(apiUrl + `/chat/room/group/${user.id}`);
  }

  getAllConversation(user: Users): Observable<any> {
    return this.http.get<Conversation[]>(apiUrl + `/chat/room/all/${user.id}`);
  }

  createGroupConversation(users: Users[]): Observable<any>{
    return this.http.post<any>(apiUrl + `/chat/group`, users);
  }

  getMessage(id: number | undefined): Observable<any> {
    return this.http.get<Messages[]>(apiUrl + `/chat/message/${id}`);
  }

  findAllMemberInConversation(conversations: Conversation[]): Observable<any> {
    return this.http.post<Users[][]>(apiUrl + `/chat/member`, conversations);
  }

  findMember(id: number | undefined): Observable<Users[]> {
    return this.http.get<Users[]>(apiUrl + `/chat/member/${id}`);
  }

  sendMessage(message: Messages): Observable<any> {
    return this.http.post<any>(apiUrl + `/chat`, message);
  }

  getPersonalConversation(id1: any, id2: any): Observable<any> {
    return this.http.get<Conversation>(apiUrl + `/chat/room/${id1}/${id2}`);
  }

  changeNameGroup(conversation: Conversation): Observable<any>{
    return this.http.put<any>(apiUrl + `/chat/changeName`, conversation);
  }

}
