import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RegistrationComponent} from './Registration/registration.component';
import {LoginComponent} from './Login/login.component';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {NgImageSliderModule} from "ng-image-slider";

import {AngularFireModule} from "@angular/fire/compat";
import {environment} from "../environments/environment";
import {AngularFireStorageModule} from "@angular/fire/compat/storage";
import {AngularFireDatabaseModule} from "@angular/fire/compat/database";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatProgressBarModule} from '@angular/material/progress-bar';
// @ts-ignore
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";


import { SearchFriendComponent } from './search-friend/search-friend.component';
import { MessageComponent } from './message/message.component';

import {ToastrModule} from "ngx-toastr";
import {MatPaginatorModule} from "@angular/material/paginator";
import {NgxPaginationModule} from "ngx-pagination";
import { ErrorComponent } from './error/error.component';
import { IndexComponent } from './index/index.component';
@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    SearchFriendComponent,
    MessageComponent,
    ErrorComponent,
    IndexComponent
  ],
    imports: [
      NgxPaginationModule,
      ToastrModule.forRoot({
        timeOut: 2000,
        positionClass: 'toast-top-right',
        preventDuplicates: true,
      }),
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,
        AngularFireModule.initializeApp(environment.firebaseConfig),
        AngularFireStorageModule,
        AngularFireDatabaseModule,
        NgImageSliderModule,
        InfiniteScrollModule,
        BrowserAnimationsModule,
        MatProgressBarModule,
        SweetAlert2Module,
        FormsModule,
      MatPaginatorModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
