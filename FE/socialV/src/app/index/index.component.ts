import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AngularFireStorage} from "@angular/fire/compat/storage";
import {UserService} from "../service/user.service";
import {finalize} from "rxjs";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent {

  user!: any;
  imageFile: any;
  path!: string;
  userId!: number;

  constructor(private userService: UserService,
              private routerActive: ActivatedRoute,
              private router: Router,
              private storage: AngularFireStorage,) {
  }

  ngOnInit() {
    {
      // @ts-ignore
      this.user = JSON.parse(sessionStorage.getItem("user")) as any;
      this.userService.findUser(this.user).subscribe(() => {
      })
    }
  }

  signOut() {
    this.router.navigate(['logout']).finally()
    sessionStorage.clear()
  }

  updateAvatar(event: any) {
    if (event.target.files && event.target.files[0]) {
      this.imageFile = event.target.files[0];
      const imagePath = `banner/${this.imageFile.name.split('.').slice(0, -1).join('.')}_${new Date().getTime()}`;
      const fileRef = this.storage.ref(imagePath);
      this.storage.upload(imagePath, this.imageFile).snapshotChanges().pipe(
        finalize(() => {
          fileRef.getDownloadURL().subscribe(url => {
            this.user.avatar = url;
            this.userService.updateUser(this.user, this.userId).subscribe(res => {
            })
          });
        })
      ).subscribe()
    }
  }
}
