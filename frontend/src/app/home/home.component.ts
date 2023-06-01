import { Component, OnInit } from '@angular/core';
import { StorageService } from '../authentication/services/storage.service';
import { DataService } from '../data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userLoggedIn: boolean = false;

  constructor(private storageService: StorageService,
              private dataService: DataService,
              private router: Router) {}

  ngOnInit(): void {
      this.userLoggedIn = this.storageService.isLoggedIn();

  }

  openRecords() {
    this.router.navigate(['/records'])
  }

  openDevices() {
    this.router.navigate(['/devices'])
  }

}
