import { Component, OnInit } from '@angular/core';
import { StorageService } from '../authentication/services/storage.service';
import { DataService } from '../data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userLoggedIn: boolean = false;

  constructor(private storageService: StorageService,
              private dataService: DataService) {}

  ngOnInit(): void {
      this.userLoggedIn = this.storageService.isLoggedIn();

      this.dataService.getRecords()
        .subscribe(res => {
          console.log(res);
        })
  }

}
