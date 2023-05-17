import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { Control } from 'leaflet';
import * as d3 from 'd3';
import { HttpClient } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  mauritaniaGeoJSON: any; // Declare a variable to store the GeoJSON data

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // Make an HTTP GET request to retrieve the GeoJSON data
    this.http.get<any>('assets/mauritania.geojson').subscribe(data => {
      // Store the GeoJSON data in the variable
      this.mauritaniaGeoJSON = data;
      
      // Initialize the Leaflet map
      const mymap = L.map('mapid').setView([20.0, -12.0], 6);

      // Add the tile layer to the map
      const tileLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
        maxZoom: 19
      });
      tileLayer.addTo(mymap);

      // Create a D3 color scale to map the city number to a color
      const colorScale = d3.scaleLinear<string>()
        .domain([0, 1])
        .range(['#FFFFB2', '#B10026']);

      // Add the GeoJSON layer to the map and style its features based on the city number
      L.geoJSON(this.mauritaniaGeoJSON, {
        style: (feature) => {
          const cityNumber = feature?.properties.number;
          console.log(feature)
          return {
            fillColor: colorScale(cityNumber),
            fillOpacity: 0.8,
            color: 'black',
            weight: 1
          }
        },
        onEachFeature: (feature, layer) => {
          const cityName = feature?.properties.name;
          const cityNumber = feature?.properties.number;
          const popupContent = `${cityName}, Risque d'inondation : ${cityNumber * 100}%`;
          layer.bindPopup(popupContent);
        }
      }).addTo(mymap);

      const legend = new Control({ position: 'topleft' });

      legend.onAdd = function(map) {
        const div = L.DomUtil.create('div', 'info legend');
        const grades = [0, 20, 40, 60, 80, 100];
        const colors = ['#FFF7C6', '#FFE08D', '#FF665E', '#FC4E2A', '#E31A1C', '#B10026'];
        const labels = [];
        for (let i = 0; i < grades.length; i++) {
          const color = colors[i];
          const label = grades[i].toString() + '%';
          const square = '<span style="display:inline-block;width:10px;height:10px;background-color:' + color + '"></span>';
          labels.push(square + ' ' + label + (grades[i + 1] ? '&ndash;' + grades[i + 1].toString() + '%<br>' : ''));
        }
        div.innerHTML = labels.join('');
        return div;
      };
      legend.addTo(mymap);
      

  })
  
}
}
