import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';

const routes: Routes = [
  {path: '', redirectTo: '/signup', pathMatch: 'full' },
  {path: 'signup', component: SignupComponent},
  {path:'login', component :LoginComponent},
  {path:'user', component :UserComponent},
  {path:'admin', component :AdminComponent},];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
