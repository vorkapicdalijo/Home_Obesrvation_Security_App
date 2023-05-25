import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }


  public registerUser(username: string, email: string, password: string): Observable<any> {
    return this.http.post(
      environment.localApiUrl + 'auth/signup',
      {username, email, password},
      httpOptions
    ).pipe(res => res as any || null);
  }

  public loginUser(username: string, password: string): Observable<any> {
    return this.http.post(
      environment.localApiUrl + 'auth/signin',
      {username, password},
      httpOptions
    ).pipe(res => res as any || null);
  }

  public logoutUser(): Observable<any> {
    return this.http.post(
      environment.localApiUrl + 'auth/signout',
      {},
      httpOptions
    ).pipe(res => res as any || null);
  }
}
