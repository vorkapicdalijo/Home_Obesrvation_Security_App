import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Device } from './models/device.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

  public getRecords(): Observable<any> {
    return this.http.get(
      environment.localApiUrl + 'main/records',
      httpOptions
    ).pipe(res => res as any || null);
  }

  public getDevices(): Observable<any> {
    return this.http.get(
      environment.localApiUrl + 'main/devices',
      httpOptions
    ).pipe(res => res as any || null);
  }

  public addDevice(deviceId: number, location: string): Observable<any> {
    return this.http.post(
      environment.localApiUrl + 'main/addDevice',
      {deviceId: deviceId,
       location: location
      }
    ).pipe(res => res as any || null);
  }
}
