import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Users} from "../Model/Users";
import {Observable} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {UserUpdate} from "../model/UserUpdate";
// @ts-ignore
import {FriendRequest} from "../Model/friend-request";
const API_URL='http://localhost:8080'
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient:HttpClient) { }

  register(user: Users): Observable<Users> {
    return this.httpClient.post<Users>(API_URL + '/signUp', user);
  }
  login(user: any): Observable<any> {
    return this.httpClient.post<any>(API_URL + '/login', user);
  }
  logOut(user:any):Observable<any>{
    return this.httpClient.post<any>(API_URL + '/logout', user);
  }
  // permissionComment(user:any):Observable<any>{
  //   return this.httpClient.post<any>(API_URL + '/permission' , user);
  // }

  findUserById(id: number): Observable<Users> {
    return this.httpClient.get<Users>(`${API_URL}/user/${id}`)
  }

  findAllFriend(id: number | undefined):Observable<Users[]>{
    return this.httpClient.get<Users[]>(`${API_URL}/user/friend/${id}`)
  }

  searchPostOnWall(id: number | undefined, content: string):Observable<any>{
    return this.httpClient.get<any>(`${API_URL}/post/wall/${id}/search?search=${content}`)
  }

  changePassword(userUpdate: UserUpdate):Observable<any>{
    return this.httpClient.put<any>(API_URL+'/changePw',userUpdate)
  }

  editProfile(user: Users):Observable<any>{
    return this.httpClient.put<any>(API_URL+"/user/" + user.id, user)
  }

  findMutualFriends(idFriend:number, idUser:number): Observable<Users[]>{
    return this.httpClient.get<Users[]>(`${API_URL}/friend/mutual/${idFriend}/${idUser}`)
  }
  findFriendOfFriend(id:number):Observable<Users[]>{
    return this.httpClient.get<Users[]>(`${API_URL}/friend/${id}`)
  }

  findListRequestFriend(id:number):Observable<Users[]>{
    return this.httpClient.get<Users[]>(`${API_URL}/friend/list/request/${id}`)
  }

  requestFriend(friendRequest: FriendRequest): Observable<FriendRequest> {
    return this.httpClient.post<FriendRequest>(`${API_URL}/friend`,friendRequest);
  }
  checkRequest(id1:number, id2:number):Observable<any>{
    return this.httpClient.get<any>(`${API_URL}/friend/checkRequest/${id1}/${id2}`);
  }
  deleteRequest(id1:number|undefined, id2:number|undefined):Observable<any> {
    return this.httpClient.delete<any>(`${API_URL}/friend/${id1}/${id2}`);
  }

  confirmRequest(id1:number|undefined, id2:number|undefined):Observable<any> {
    return this.httpClient.get<any>(`${API_URL}/friend/accept/${id1}/${id2}`);
  }
  findUsersByNameContaining(name:string):Observable<Users[]>{
    return this.httpClient.get<Users[]>(API_URL +"/user/search?search="+name)
  }
  // loginAdmin(user: Users): Observable<Users> {
  //   return this.httpClient.post<Users>(API_URL + '/admin/login', user);
  // }
  showAllUser():Observable<Users[]>{
    return this.httpClient.get<Users[]>(API_URL+"/getAllUser")
  }
  // blockAndActive(user: Users):Observable<any>{
  //   return this.httpClient.post<any>(API_URL+"/admin",user)
  // }

  getListCountMutualFriend(id:number,user:Users[]):Observable<any>{
    return this.httpClient.post<any>(`${API_URL}/friend/mutual/search/${id}`,user)
  }

  countFriend(users:Users[]):Observable<any>{
    return this.httpClient.post<any>(API_URL+"/friend/sum",users)
  }

  findAllUser(): Observable<Users[]> {
    return this.httpClient.get<Users[]>(`http://localhost:8080/user`)
  }

  saveUser(user: Users): Observable<any> {
    return this.httpClient.post<Users>(`http://localhost:8080/user`, user)
  }

  findUser(id: number): Observable<Users> {
    return this.httpClient.get<Users>(`http://localhost:8080/user/${id}`)
  }

  updateUser(user:Users,id:number) : Observable<any> {
    return this.httpClient.put<Users>(`http://localhost:8080/user/${user.id}`,user)
  }
}
