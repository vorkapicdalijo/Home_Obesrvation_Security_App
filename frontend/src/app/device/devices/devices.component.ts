import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { DataService } from 'src/app/data.service';
import { Device } from 'src/app/models/device.model';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { WarningDialogComponent } from 'src/app/warning-dialog/warning-dialog.component';
import { StorageService } from 'src/app/authentication/services/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
})
export class DevicesComponent implements OnInit {

  displayedColumns = ['deviceId', 'location', 'timestamp', 'actions'];
  dataSource!: MatTableDataSource<Device>;

  devices!: Device[];
  formVisible: boolean = false;

  deviceForm!: FormGroup;
  isLoading: boolean = true;

  constructor(private service: DataService,
              private fb: FormBuilder,
              public dialog: MatDialog,
              private storageService: StorageService,
              private router: Router) {

  }

  ngOnInit(): void {
    this.deviceForm = this.fb.group({
      deviceId: new FormControl('', Validators.required),
      location: new FormControl('', Validators.required),
    });

    this.loadDevices();
  }

  loadDevices() {
    this.isLoading = true;
    this.service.getDevices()
      .subscribe(devices => {
        this.dataSource = new MatTableDataSource<Device>(devices);
        this.devices = devices;
        this.isLoading = false;
      })
  }

  onSubmit() {
    let deviceId = this.deviceForm.get('deviceId')?.value;
    let location = this.deviceForm.get('location')?.value;
    this.service.addDevice(deviceId, location).subscribe(
      res => {
        this.loadDevices();
      }
    )
  }

  removeDevice(id: string) {
        
    const dialogRef = this.dialog.open(WarningDialogComponent,
      {
        position: {top: '30px'},
        data: {
          id: id,
          type: 'device'
        }
      })

    dialogRef.afterClosed().subscribe(res => {
      if(res) {
        this.service.deleteDevice(id)
          .subscribe(res => {
            this.loadDevices();
          });
      }
    });
  }

  isUniqueId() {
    return this.devices.filter(device => {return device.deviceId.toLowerCase() == this.deviceForm.get('deviceId')?.value.toLowerCase()}).length == 0
  }

  showForm() {
    this.formVisible = !this.formVisible;
  }

}
