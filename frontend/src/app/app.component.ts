import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { initializeApp } from '@firebase/app';
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { environment } from 'src/environments/environment';
import { StorageService } from './authentication/services/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  message: any = null;

  constructor(private http: HttpClient,
              private sessionStorage: StorageService){}


  ngOnInit(): void {
    this.requestPermission();
    this.listen();
  }

  requestPermission() {
    const messaging = getMessaging();

    getToken(messaging,
     { vapidKey: environment.FirebaseConfig.vapidKey}).then(
       (currentToken) => {
         if (currentToken) {
           console.log(currentToken);
           this.sessionStorage.saveToken(currentToken);

          //  this.http.post('http://localhost:8080/api/firebase/send-notification/token',
          //   {
          //     title: 'test',
          //     content: 'content',
          //     image: 'slika'
          //   },
          //   {
          //     params: {
          //       token: currentToken
          //     }
          //   }
          //  ).subscribe(() => {  });

         } else {
           console.log('No registration token available. Request permission to generate one.');
         }
     }).catch((err) => {
        console.log('An error occurred while retrieving token. ', err);
    });
  }

  listen() {
    const messaging = getMessaging();

    onMessage(messaging, (payload) => {
      console.log('Message received. ', payload);
      this.message=payload;
    });
  }
}
