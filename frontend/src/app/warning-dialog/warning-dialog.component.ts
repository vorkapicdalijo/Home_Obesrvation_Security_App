import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export interface DialogData {
  id: string;
  type: string;
}

@Component({
  selector: 'app-warning-dialog',
  templateUrl: './warning-dialog.component.html',
  styleUrls: ['./warning-dialog.component.css']
})
export class WarningDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<WarningDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: DialogData){}


  ngOnInit(): void {
  }


  onNoClick() {
    this.dialogRef.close();
  }
}
