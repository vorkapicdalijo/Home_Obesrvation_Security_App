import { Component, OnInit } from '@angular/core';
import { StorageService } from '../authentication/services/storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userLoggedIn: boolean = false;

  constructor(private storageService: StorageService) {}

  ngOnInit(): void {
      this.userLoggedIn = this.storageService.isLoggedIn();
  }

}
