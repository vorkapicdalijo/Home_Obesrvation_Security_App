import { Component, OnInit } from '@angular/core';
import { StorageService } from '../authentication/services/storage.service';
import { AuthenticationService } from '../authentication/services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private storageService: StorageService,
              private authenticationService: AuthenticationService,
              private router: Router){

  }

  ngOnInit(): void {
      this.isLoggedIn();
  }

  public isLoggedIn() {
    return this.storageService.isLoggedIn();
  }

  public logout() {
    this.storageService.logoutUser();
    window.location.reload()
  }

  returnToHome() {
    this.router.navigate(['/home'])
  }

}
