import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AdminComponent } from './components/admin/admin.component';
import { InodationZonesComponent } from './components/inodation-zones/inodation-zones.component';
import { FloudpredictionComponent } from './components/floudprediction/floudprediction.component';
import { NgChartsModule } from 'ng2-charts';
import { MapComponent } from './components/map/map.component';
import { MatTableModule } from '@angular/material/table';
import { CdkTableModule } from '@angular/cdk/table';
import { UserComponent } from './components/user/user.component';
import { HeaderComponent } from './components/header/header.component';
import { VisualiserComponent } from './components/visualiser/visualiser.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    AdminComponent,
    InodationZonesComponent,
    FloudpredictionComponent,
    MapComponent,
    UserComponent,
    HeaderComponent,
    VisualiserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgChartsModule,
    MatTableModule,
    CdkTableModule
  ],
  exports: [
    MatTableModule,
    CdkTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
