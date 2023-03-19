import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Users} from "../Model/Users";
import {UserService} from "../service/user.service";
import Swal from "sweetalert2";
import {ToastrService} from "ngx-toastr";
import {Stomp} from "@stomp/stompjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  loginForm!:FormGroup
  user!:Users
  private stompClient: any;
  userList: Users[] = [];

  constructor(private userService: UserService,
              private router: Router,
              private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.connect()
    this.userService.showAllUser().subscribe((data) => {
      this.userList = data
    })

    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]*$')]),
      password: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]*$'), Validators.minLength(6), Validators.maxLength(32)])
    });
  }

  connect() {
    const socket = new WebSocket('ws://localhost:8080/ws/websocket');
    this.stompClient = Stomp.over(socket);
    const _this = this;
    this.stompClient.connect({}, function () {
      _this.stompClient.subscribe('/topic/greetings', function (notification: any) {

  })
    })
  }

  login() {
    this.user = this.loginForm.value
    // @ts-ignore
    this.user.checkOn = true
    let flag=true
    if (this.checkUsernameAndPassMatch((this.loginForm.get('username')?.value), this.loginForm.get('password')?.value)) {
        if (flag){
      this.userService.login(this.user).subscribe(user => {
        window.localStorage.setItem("user", JSON.stringify(user));
        this.success()
          flag=false
        this.sendNotification()
          this.router.navigate(['/message']);
      }, err => {
        this.warning()
          flag=false
      }
      )
          flag=false
        }
    }       if (flag){ this.error()}

  }

  success(): void {
    this.toastr.success('Login Success !', 'Success');
  }

  error(): void {
    this.toastr.error('Password or Username not match !', 'Error')
  }

  warning(): void {
    this.toastr.warning('Account be blocked', 'Warning')
  }

  checkUsernameAndPassMatch(username: string, pass: string): boolean {
    for (let i = 0; i < this.userList.length; i++) {
      if (this.userList[i].username == username && this.userList[i].password == pass) {
        return true;
      }
    }
    return false;
  }


  sendNotification(){
    // @ts-ignore
    this.stompClient.send('/app/hello',{}, "");
  }
}
