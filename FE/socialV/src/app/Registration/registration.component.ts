import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Users} from "../Model/Users";
import Swal from "sweetalert2";
import {ToastrService} from "ngx-toastr";


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  registerForm!: FormGroup
  user!: Users
  userList: Users[]=[];


  constructor(private userService: UserService,
              private router: Router,
              private routerActive: ActivatedRoute,
              private toastr:ToastrService) {
  }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]*$')]),
      password: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]*$'), Validators.minLength(6), Validators.maxLength(32)]),
      confirmPassword: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]*$'), Validators.minLength(6), Validators.maxLength(32)]),
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.pattern("^(.+)@(\\S+)$")]),
      phone: new FormControl('', [Validators.required, Validators.pattern("^[0-9]{10}$")]),
      birthday: new FormControl('', [Validators.required])
    })


  }

  checkEmailExist(email:string):boolean {
    for (let i = 0; i < this.userList.length; i++) {
      if (this.userList[i].email==email){
        return true;
        break;
      }
    }return false;
  }
  checkUserNameExist(username:string):boolean{
    for (let i = 0; i < this.userList.length; i++) {
      if (this.userList[i].username==username){
        return true;
        break;
      }
    }return false;  }

  register() {
    this.user = this.registerForm.value;
    let flag=true
    if (!this.checkUserNameExist(this.registerForm.get('username')?.value)){
      if (!this.checkEmailExist(this.registerForm.get('email')?.value)){
        if (this.registerForm.get('password')?.value==this.registerForm.get('confirmPassword')?.value){
       if (flag){ this.userService.register(this.user).subscribe(data => {
            window.localStorage.setItem("user", JSON.stringify(data));
            this.router.navigate(['']);
            this.success()
          }
        )
         flag=false
       }
    } if (flag){this.errorPassword()
          flag=false
        }
  }if (flag) {
        this.errorEmail()
        flag=false
      }

    }  if (flag){
      this.errorUsername()
    }
  }
  success(): void {
    this.toastr.success('Sign Up Successfully !', 'Success');
  }
  errorEmail():void{
    this.toastr.error('Email is existing !','Error')
  }
  errorUsername():void{
    this.toastr.error('Username is existing !','Error')
  }
  errorPassword():void{
    this.toastr.warning('Password & Confirm Password is not match ','Warning')
  }
}
