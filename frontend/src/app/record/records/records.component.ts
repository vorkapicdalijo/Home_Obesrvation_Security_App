import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { testImagePath } from 'src/app/constants/constants';
import { DataService } from 'src/app/data.service';
import { Record } from 'src/app/models/record.model';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { WarningDialogComponent } from 'src/app/warning-dialog/warning-dialog.component';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css'],
})
export class RecordsComponent implements OnInit {
  records!: any[];

  devices!: any[];
  currentDevice: any = null;

  imgPath = testImagePath;

  isLoading = true;

  deviceForm!: FormGroup;

  constructor(
    private dataService: DataService,
    private router: Router,
    private fb: FormBuilder,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.deviceForm = this.fb.group({
      deviceFormControl: new FormControl('', Validators.required),
    });

    this.loadRecords();
    this.loadDevices();
  }

  loadRecords() {
    this.isLoading = true;
    this.dataService.getRecords().subscribe((res) => {
      this.records = res;
      this.isLoading = false;
    });
  }

  loadDevices() {
    this.dataService.getDevices().subscribe((res) => {
      this.devices = res;
    });
  }

  clearFilter() {
    this.currentDevice = null;
    this.loadRecords();
  }

  openRecordDetails(id: number) {
    this.router.navigate([`/records/${id}`]);
  }

  getTimestamp(timestamp: number) {
    let date = new Date(timestamp);
    return date.toISOString().replace('T', ' ,  ').replace('Z', '');
  }

  removeRecord(id: number) {
    const dialogRef = this.dialog.open(WarningDialogComponent,
      {
        position: {top: '30px'},
        data: {
          id: id,
          type: 'record'
        }
      })

    dialogRef.afterClosed().subscribe(res => {
      if(res) {
        this.dataService.deleteRecord(id)
          .subscribe(res => {
            this.loadRecords();
          });
      }
    });
  }

  getRecordsForDevice() {
    this.isLoading = true;
    this.currentDevice = this.deviceForm.get('deviceFormControl')?.value;
    let deviceId = this.currentDevice.split(', ')[0];

    this.dataService.getRecordsByDeviceId(deviceId).subscribe((res) => {
      this.records = res;
      this.isLoading = false;
    });
  }
}
