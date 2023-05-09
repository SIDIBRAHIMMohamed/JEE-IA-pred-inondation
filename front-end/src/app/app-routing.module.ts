import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { AdminComponent } from './components/admin/admin.component';
import { InodationZonesComponent } from './components/inodation-zones/inodation-zones.component';
import { FloudpredictionComponent } from './components/floudprediction/floudprediction.component';
import { MapComponent } from './components/map/map.component';

const routes: Routes = [
  {path:'', redirectTo: '/signup', pathMatch: 'full' },
  {path:'signup', component: SignupComponent},
  {path:'login', component :LoginComponent},
  {path:'admin', component :AdminComponent},
  {path:'floodpred', component :FloudpredictionComponent},
  {path:'inondation', component :InodationZonesComponent},
  {path:'map', component :MapComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
