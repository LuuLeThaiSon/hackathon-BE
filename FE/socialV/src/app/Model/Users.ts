import {Role} from "./Role";

export interface Users{
  id?:number
  username?:string
  password?:string
  confirmPassword?:string
  name?:string
  email?:string
  phone?:string
  birthday?:string
  status?:true
  checkOn?:false
  seeFriendPermission?:true
  commentPermission?:true
  avatar?:string
  address?:string
  hobby?:string
  role?:Role
}
