import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { StorageService } from './storage.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient,
              private sessionStorage: StorageService) { }


  public registerUser(username: string, email: string, password: string, confirmPassword: string): Observable<any> {
    let firebaseToken = window.sessionStorage.getItem('notificationToken');
    return this.http.post(
      environment.localApiUrl + 'auth/signup',
      {username, email, password, confirmPassword, firebaseToken},
      httpOptions
    ).pipe(res => res as any || null);
  }

  public loginUser(username: string, password: string): Observable<any> {
    return this.http.post(
      environment.localApiUrl + 'auth/login',
      {username, password},
      httpOptions
    ).pipe(res => res as any || null);
  }

  public logoutUser(): Observable<any> {
    return this.http.post(
      environment.localApiUrl + 'auth/logout',
      {},
      httpOptions
    ).pipe(res => res as any || null);
  }
}
