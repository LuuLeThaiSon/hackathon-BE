import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegistrationComponent} from "./Registration/registration.component";
import {LoginComponent} from "./Login/login.component";

import {SearchFriendComponent} from "./search-friend/search-friend.component";

import {MessageComponent} from "./message/message.component";
import {IndexComponent} from "./index/index.component";

const routes: Routes = [
  {
    path: 'Registration', component: RegistrationComponent
  },
  {
    path: '', component:LoginComponent
  },
  {
    path: 'home', component:IndexComponent
  },

  {
    path: 'SearchFriend', component: SearchFriendComponent
  },

  {
    path: 'message', component: MessageComponent
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
