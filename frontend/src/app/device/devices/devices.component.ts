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

  constructor(private service: DataService,
              private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.deviceForm = this.fb.group({
      deviceId: new FormControl('', Validators.required),
      location: new FormControl('', Validators.required),
    });

    this.loadDevices();
  }

  loadDevices() {
    this.service.getDevices()
      .subscribe(devices => {
        this.dataSource = new MatTableDataSource<Device>(devices);
        this.devices = devices;
      })
  }

  onSubmit() {
    let deviceId = this.deviceForm.get('deviceId')?.value;
    let location = this.deviceForm.get('location')?.value;
    //let timestamp = new Date().getTime().toString();
    this.service.addDevice(deviceId, location).subscribe(
      res => {
        console.log(res)
        window.location.reload();
      },
      error => {
        console.log(error)
        window.location.reload();
      }
    )
  }

  showForm() {
    this.formVisible = !this.formVisible;
  }

}
