import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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

  constructor(private route: ActivatedRoute,
              private dataService: DataService,
              private sanitizer: DomSanitizer) {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.recordId = params['id']
    })
  
    this.dataService.getRecordById(this.recordId)
      .subscribe(record => {
        console.log(record);

        this.imgPath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' 
        + record.imageDisplay);
      });
  }


}
