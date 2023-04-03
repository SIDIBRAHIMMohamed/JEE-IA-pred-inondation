import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-inodation-zones',
  templateUrl: './inodation-zones.component.html',
  styleUrls: ['./inodation-zones.component.css']
})
export class InodationZonesComponent implements OnInit {
  zones: any[] = [];
  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  fetchZones() {
    this.http.get('api/inondationZones').subscribe((response: any) => {
      this.zones = response;
    });
  }
}
