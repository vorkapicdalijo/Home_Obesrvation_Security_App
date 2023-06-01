import { AuthGuard } from './authentication/guard/auth.guard';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterComponent } from './authentication/register/register.component';
import { HomeComponent } from './home/home.component';
import { RecordsComponent } from './record/records/records.component';
import { RecordDetailsComponent } from './record/record-details/record-details.component';
import { DevicesComponent } from './device/devices/devices.component';


const routes: Routes = [

  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'records',
    component: RecordsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'records/:id',
    component: RecordDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'devices',
    component: DevicesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: 'home'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
