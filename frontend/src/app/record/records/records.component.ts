import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/data.service';
import { Record } from 'src/app/models/record.model';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css']
})
export class RecordsComponent implements OnInit {

  records!: any[];

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    this.dataService.getRecords()
        .subscribe(res => {
          console.log(res);
          this.records = res;
        })
  }
}
