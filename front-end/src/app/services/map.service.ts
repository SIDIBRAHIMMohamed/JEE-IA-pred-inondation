import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { forkJoin } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  constructor(private http: HttpClient) {}

  getGeoJSONData(): Observable<any> {
    return this.http.get<any>('assets/output.geojson');
  }

  getCityData(): Observable<any> {
    return this.http.get<any>('http://localhost:8082/api/inondationZones/lastInserted');
  }

  getMapData(): Observable<any[]> {
    const geoJSON$ = this.getGeoJSONData();
    const cityData$ = this.getCityData();

    return forkJoin([geoJSON$, cityData$]);
  }
}
