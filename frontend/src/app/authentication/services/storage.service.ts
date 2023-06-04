import { of, delay } from 'rxjs';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() {}

  clean(): void {
    window.sessionStorage.clear();
  }

  public saveUser(userResponse: any): void {
    const helper = new JwtHelperService();

    let user: Object = {
      accessToken: userResponse.accessToken
    }

    const expirationDate = helper.getTokenExpirationDate(userResponse.accessToken);


    let timeout = helper.getTokenExpirationDate(userResponse.accessToken)!.valueOf() - new Date().valueOf();

    this.expirationCounter(timeout);

    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  expirationCounter(timeout: any) {
    // of(null).pipe(delay(timeout)).subscribe((expired) => {
    //   this.logoutUser();
    //   window.location.reload()
    // });
    setTimeout(() => {
      this.logoutUser()
      window.location.reload()
    }, timeout)
  }

  public saveToken(token: string) {
    window.sessionStorage.setItem('notificationToken', token);
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }

  public logoutUser(): any {
    window.sessionStorage.removeItem(USER_KEY);
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return true;
    }

    return false;
  }
}
