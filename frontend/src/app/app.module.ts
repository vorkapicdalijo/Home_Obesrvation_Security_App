import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { HttpRequestInterceptor } from './interceptors/auth.interceptor';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTableModule } from '@angular/material/table';
import { RegisterComponent } from './authentication/register/register.component';
import { LoginComponent } from './authentication/login/login.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthGuard } from './authentication/guard/auth.guard';
import { environment } from '../environments/environment';

import { initializeApp } from "firebase/app";
import { RecordsComponent } from './record/records/records.component';
import { RecordDetailsComponent } from './record/record-details/record-details.component';
import { DevicesComponent } from './device/devices/devices.component';

initializeApp(environment.FirebaseConfig)

@NgModule({
  declarations: [
    AppComponent,
    AuthenticationComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    RecordsComponent,
    RecordDetailsComponent,
    DevicesComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatTableModule,
    AppRoutingModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor ,
      multi: true
    }, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
