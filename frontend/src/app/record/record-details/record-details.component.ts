import { testImagePath } from './../../constants/constants';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from 'src/app/data.service';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-record-details',
  templateUrl: './record-details.component.html',
  styleUrls: ['./record-details.component.css']
})
export class RecordDetailsComponent implements OnInit {

  recordId: number = 0;
  imgPath: any = null;
  timestamp: string = "";
  record: any = null;

  isLoading: boolean = true;

  constructor(private route: ActivatedRoute,
              private dataService: DataService,
              private sanitizer: DomSanitizer,
              private router: Router) {

  }

  ngOnInit(): void {
    this.isLoading = true;
    this.route.params.subscribe(params => {
      this.recordId = params['id']
    })

    this.dataService.getRecordById(this.recordId)
      .subscribe(record => {
        this.record = record;
        let date = new Date(this.record.timestamp)
        this.timestamp = date.toISOString().replace("T", " ,  ").replace("Z", "")

        this.isLoading = false;
      });
  }

  returnToRecords() {
    this.router.navigate(['/records'])
  }


}
