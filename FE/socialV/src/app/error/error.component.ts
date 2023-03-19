import {Component, OnInit} from '@angular/core';
import {Stomp} from "@stomp/stompjs";
import * as moment from "moment";

import {Users} from "../Model/Users";
import {Conversation} from "../Model/conversation";

import {UserService} from "../service/user.service";
import {AngularFireStorage} from "@angular/fire/compat/storage";

import {Router} from "@angular/router";
import {ChatService} from "../chatService/chat.service";

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit{

  data = localStorage.getItem("user")
  // @ts-ignore
  user: Users = JSON.parse(this.data)
  listFriend: Users[] = [];
  listRequest: Users[] = [];

  countNotSeen:number = 0
  timeNotificationMoment: any[] = [];
  countOther: any[] = [];
  listMutualFriend: number[] = [];
  private stompClient: any;
  // @ts-ignore
  listSearchFriend:Users[]= JSON.parse(localStorage.getItem("listUser"))
  // @ts-ignore
  search:string= JSON.parse(localStorage.getItem("nameUser"))

  listAllConversation: Conversation[] = [];
  listMemberName: any [] = []
  listAvatarMember: any [] = []
  ngOnInit(): void {
    this.findAllFriend()
    // this.onMoveTop()
    this.findListRequest()
    this.connect()
    this.findMutualFriend()
    this.getAllConversation()
  }


  constructor(
              private userService: UserService,
              private storage: AngularFireStorage,

              private router: Router,
              private chatService:ChatService) {
  }


  connect(){
    const socket = new WebSocket('ws://localhost:8080/ws/websocket');
    this.stompClient = Stomp.over(socket);
    const _this = this;
    this.stompClient.connect({}, function (){
      _this.stompClient.subscribe('/topic/greetings', function (notification: any) {
        _this.findListRequest()
      })
    })
  }

  findMutualFriend() {
    // @ts-ignore
    this.userService.getListCountMutualFriend(this.user.id,this.listSearchFriend).subscribe((data) => {
      this.listMutualFriend = data
    })
  }

  sendNotification(){
    // @ts-ignore
    this.stompClient.send('/app/hello',{}, this.user.id.toString());
  }






  findAllFriend() {
    // @ts-ignore
    this.userService.findAllFriend(this.user.id).subscribe((data) => {
      this.listFriend = data
    })
  }


  confirmRequest(friendRequestId: any) {
    this.userService.confirmRequest(this.user.id, friendRequestId).subscribe(() => {
      this.findListRequest()
      this.sendNotification()
    })
  }

  findListRequest() {
    // @ts-ignore
    this.userService.findListRequestFriend(this.user.id).subscribe((data) => {
      this.listRequest = data

    })
  }


  logOut() {
    this.userService.logOut(this.user).subscribe(()=>{
      localStorage.removeItem("user");
      this.router.navigate(['']);
    })
  }

  deleteRequest(friendRequestId: any) {
    this.userService.deleteRequest(this.user.id, friendRequestId).subscribe(() => {
      this.findListRequest()
      this.sendNotification()
    })
  }

  searchUserByNameContaining(name:string){
    this.userService.findUsersByNameContaining(name).subscribe(data=>{

      this.listSearchFriend=data
    })
  }

  getAllConversation() {
    // @ts-ignore
    this.chatService.getAllConversation(this.user).subscribe(data => {
      this.listAllConversation = data
      this.chatService.findAllMemberInConversation(data).subscribe(dataMember => {
        for (let i = 0; i < dataMember.length; i++) {
          if (this.listAllConversation[i].type === 1) {
            for (let j = 0; j < dataMember[i].length; j++) {
              if (dataMember[i][j].id !== this.user.id) {
                this.listMemberName.push(dataMember[i][j].name)
                this.listAvatarMember.push(dataMember[i][j].avatar)
                break;
              }
            }
          } else {
            this.listAvatarMember.push("https://phunugioi.com/wp-content/uploads/2021/11/Hinh-anh-nhom-ban-than-tao-dang-vui-ve-ben-bo-bien-395x600.jpg")
            if (data[i].name !== null){
              this.listMemberName.push(data[i].name)
            }else {
              this.listMemberName[i] = ""
              for (let j = 0; j < dataMember[i].length; j++) {
                this.listMemberName[i] += dataMember[i][j].name
                if (j < dataMember[i].length - 1) {
                  this.listMemberName[i] += `, `;
                }
              }
            }
          }
        }
      })
    })
  }
}
