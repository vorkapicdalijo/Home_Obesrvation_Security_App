import { Component, OnInit } from '@angular/core';
import { StorageService } from '../authentication/services/storage.service';
import { AuthenticationService } from '../authentication/services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private storageService: StorageService,
              private authenticationService: AuthenticationService){

  }

  ngOnInit(): void {
      this.isLoggedIn();
  }

  public isLoggedIn() {
    return this.storageService.isLoggedIn();
  }

  public logout() {
    this.authenticationService.logoutUser()
      .subscribe(res => {
        this.storageService.logoutUser();
        window.location.reload()
      })
  }

}
