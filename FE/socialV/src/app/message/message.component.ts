import {AfterViewChecked, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Users} from "../Model/Users";
import {UserService} from "../service/user.service";
import {AngularFireStorage} from "@angular/fire/compat/storage";
import {Router} from "@angular/router";
import {Stomp} from "@stomp/stompjs";
import * as moment from "moment";
import {ChatService} from "../chatService/chat.service";
import {Conversation} from "../Model/conversation";
import {Messages} from "../Model/message";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit, AfterViewChecked {
  data = localStorage.getItem("user")
  // @ts-ignore
  user: Users = JSON.parse(this.data)
  listFriend: Users[] = [];
  listRequest: Users[] = [];
  listPersonalConversation: Conversation[] = [];
  listAllConversation: Conversation[] = [];
  listGroupConversation: Conversation[] = [];
  listUserConversation: Users[][] = [];
  listUserGroup: Users[][] = [];
  listNameGroup: string[] = [];
  // @ts-ignore
  listSearchFriend:Users[]= JSON.parse(localStorage.getItem("listUser"))

  listStringUserConversation: string[] = [];
  listStringUserImgConversation: string[] = [];
  userNameChat?: string;
  userImgChat?: string;
  listMessage: Messages[] = [];
  countNotSeen: number = 0
  timeNotificationMoment: any[] = [];
  countOther: any[] = [];
  userSearch: Users[] = [];
  userAddGroup: Users[] = [];
  listMutualFriend: number[] = [];
  private stompClient: any;
  listMemberName: any [] = []
  listAvatarMember: any [] = []
  conversationNow!: Conversation
  messageForm: FormGroup = new FormGroup({
    content: new FormControl()
  })

  searchPeople: FormGroup = new FormGroup({
    searchInput: new FormControl()
  })
  // @ts-ignore
  search: string = JSON.parse(localStorage.getItem("nameUser"))

  ngOnInit(): void {
    this.findAllFriend()
    this.findListRequest()
    this.connect()
    this.getAllPersonalConversation()
    this.getAllGroupConversation()
    this.getAllConversation()

  }

  constructor(private userService: UserService,
              private storage: AngularFireStorage,
              private chatService: ChatService,
              private router: Router) {
  }

  ngAfterViewChecked(): void {
    if (this.conversationNow != null) {
      document.getElementById("showChat")!.click()
    }
  }

  getChatRoom(conversation: Conversation){
    window.localStorage.setItem("roomChat", JSON.stringify(conversation));
  }

  sendMessage(conversation: Conversation) {
    const message: Messages = this.messageForm.value
    message.users = this.user
    message.conversation = conversation
    this.chatService.sendMessage(message).subscribe(() => {
      this.getAllPersonalConversation()
      this.getAllGroupConversation()
      this.chatService.getMessage(conversation.id).subscribe(data => {
        this.listMessage = data
        this.messageForm.reset()
        this.sendNotification()
      })
    })
  }

  searchMember(name: string) {
    this.userSearch = []
    this.userSearch = this.listFriend.filter(user => {
      return user.name?.toLowerCase().includes(name.toLowerCase())
    })
  }

  addToGroup(user: Users) {
    this.userAddGroup.push(user)
  }

  submitGroup() {
    if (this.userAddGroup.length > 0) {
      this.userAddGroup.push(this.user)
      this.chatService.createGroupConversation(this.userAddGroup).subscribe(() => {
        this.getAllGroupConversation()
        document.getElementById("close-modal")!.click()

        this.userAddGroup = []
      })
    }
  }

  closeModal() {
    // @ts-ignore
    this.searchPeople.reset()
    this.userAddGroup = []
    this.userSearch = []
  }

  deleteMemberFromGroup(index: number) {
    this.userAddGroup.splice(index, 1)
  }

  openChat(id: number| undefined){
    this.chatService.getPersonalConversation(this.user.id, id).subscribe(data =>{
      this.fromFriendProfile(data)
      this.listSearchFriend = []
    })
  }

  fromFriendProfile(conv: Conversation) {
    // @ts-ignore
    this.conversationNow = conv
    if (this.conversationNow !== null) {
      this.chatService.getMessage(this.conversationNow?.id).subscribe(data => {
        this.listMessage = data
        this.chatService.findMember(this.conversationNow?.id).subscribe(data => {
          if (this.conversationNow.type == 1) {
            for (let i = 0; i < data.length; i++) {
              if (data[i].id !== this.user.id) {
                this.userNameChat = data[i].name
                this.userImgChat = data[i].avatar
                break;
              }
            }
          }else {
            this.userImgChat = "https://phunugioi.com/wp-content/uploads/2021/11/Hinh-anh-nhom-ban-than-tao-dang-vui-ve-ben-bo-bien-395x600.jpg"
            if (this.conversationNow.name != null){
              this.userNameChat = this.conversationNow.name
            }else {
              this.userNameChat = ""
              for (let j = 0; j < data.length; j++) {
                this.userNameChat += data[j].name
                if (j < data.length - 1) {
                  this.userNameChat += `, `;
                }
              }
            }
          }
          localStorage.removeItem("roomChat");
        })
      })
    }
  }

  changeNameGroup(conversation: Conversation, name: string) {
    conversation.name = name
    this.chatService.changeNameGroup(conversation).subscribe(() => {
      document.getElementById("change-name")!.click()
      this.getAllGroupConversation()
      this.userNameChat = name
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

  getAllPersonalConversation() {
    this.chatService.getAllPersonalConversation(this.user).subscribe(data => {
      this.listPersonalConversation = data
      this.chatService.findAllMemberInConversation(this.listPersonalConversation).subscribe(data => {
        this.listUserConversation = data
        for (let i = 0; i < this.listUserConversation.length; i++) {
          for (let j = 0; j < this.listUserConversation[i].length; j++) {
            if (this.listUserConversation[i][j].id !== this.user.id) {
              // @ts-ignore
              this.listStringUserConversation[i] = this.listUserConversation[i][j].name
              // @ts-ignore
              this.listStringUserImgConversation[i] = this.listUserConversation[i][j].avatar
            }
          }
        }
      })
    })
  }
  searchUserByNameContaining(name:string){
    this.userService.findUsersByNameContaining(name).subscribe(data=>{

      this.listSearchFriend=data
    })
  }

  getAllGroupConversation() {
    this.chatService.getAllGroupConversation(this.user).subscribe(data => {
      this.listGroupConversation = data
      this.chatService.findAllMemberInConversation(this.listGroupConversation).subscribe(data => {
        this.listUserGroup = data;
        this.listNameGroup = []
        for (let i = 0; i < this.listGroupConversation.length; i++) {
          if (this.listGroupConversation[i].name != null) {
            // @ts-ignore
            this.listNameGroup[i] = this.listGroupConversation[i].name
          } else {
            this.listNameGroup[i] = "";
            for (let j = 0; j < this.listUserGroup[i].length; j++) {
              this.listNameGroup[i] += this.listUserGroup[i][j].name
              if (j < this.listUserGroup[i].length - 1) {
                this.listNameGroup[i] += `, `;
              }
            }
          }
        }
      })
    })
  }

  transferChatDetail(id: number, conversation: Conversation, typeId: number) {
    this.chatService.getMessage(conversation?.id).subscribe(data => {
      this.conversationNow = conversation
      this.listMessage = data
      if (typeId == 1) {
        this.userNameChat = this.listStringUserConversation[id]
        this.userImgChat = this.listStringUserImgConversation[id]
      }
      if (typeId == 2) {
        this.userNameChat = this.listNameGroup[id]
        this.userImgChat = "https://phunugioi.com/wp-content/uploads/2021/11/Hinh-anh-nhom-ban-than-tao-dang-vui-ve-ben-bo-bien-395x600.jpg"
      }
    })
  }

  connect() {
    const socket = new WebSocket('ws://localhost:8080/ws/websocket');
    this.stompClient = Stomp.over(socket);
    const _this = this;
    this.stompClient.connect({}, function () {
      _this.stompClient.subscribe('/topic/greetings', function (notification: any) {
        _this.findListRequest()
        _this.findAllFriend()
        _this.getAllPersonalConversation()
        _this.chatService.getMessage(_this.conversationNow.id).subscribe(data => {
          _this.listMessage = data
          _this.messageForm.reset()
        })
      })
    })
  }

  sendNotification() {
    // @ts-ignore
    this.stompClient.send('/app/hello', {}, this.user.id.toString());
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
    this.userService.logOut(this.user).subscribe(() => {
      localStorage.removeItem("user");
      this.sendNotification()
      this.router.navigate(['']);
    })
  }

  deleteRequest(friendRequestId: any) {
    this.userService.deleteRequest(this.user.id, friendRequestId).subscribe(() => {
      this.findListRequest()
      this.sendNotification()
    })
  }
}


