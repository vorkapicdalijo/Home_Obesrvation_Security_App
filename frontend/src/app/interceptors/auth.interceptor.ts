import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //if(!req.url.includes("login") && !req.url.includes("logout") && !req.url.includes("signup")) {
    const user = JSON.parse(window.sessionStorage.getItem('auth-user') || '{}');
    const token = user.accessToken
    if(token) {
      req = req.clone({
        headers: req.headers.set("Authorization", "Bearer "+token)
      });
    }

    return next.handle(req);
  }
}
