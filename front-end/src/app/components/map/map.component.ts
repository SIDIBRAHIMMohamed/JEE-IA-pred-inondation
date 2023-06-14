import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { Control } from 'leaflet';
import * as d3 from 'd3';
import { forkJoin } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';
import { MapService } from 'src/app/services/map.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  mauritaniaGeoJSON: any;

  constructor(private mapService: MapService) { }

  ngOnInit(): void {

    this.mapService.getMapData().subscribe(results => {
      const geoJSONData = results[0];
      const cityData = results[1];

      this.mauritaniaGeoJSON = geoJSONData;

      const latitude = 17.05786;
      const longitude = -10.95173;

      
      const mymap = L.map('mapid').setView([latitude, longitude], 6);

      const tileLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
        maxZoom: 9,
        minZoom: 6
      });
      tileLayer.addTo(mymap);

      // Create a D3 color scale to map the city number to a color
      const colorScale = d3.scaleLinear<string>()
        .domain([0, 1])
        .range(['#FFFFB2', '#B10026']);

      // Add the GeoJSON layer to the map and style its features based on the city number
      L.geoJSON(this.mauritaniaGeoJSON, {
        style: (feature) => {
          const cityName = feature?.properties.ADM2_EN;
          const cityNumber = this.getMoughataaProbability(cityName, cityData);
          return {
            fillColor: colorScale(cityNumber),
            fillOpacity: 0.2,
            color: 'black',
            weight: 1
          };
        },
        onEachFeature: (feature, layer) => {
          const cityName = feature?.properties.ADM2_EN;
          const cityNumber = this.getMoughataaProbability(cityName, cityData);
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
      }
    );
  }



  getMoughataaProbability(cityName: string, cityData: any[]): number {
    const city = cityData.find(city => city.cityName === cityName);
    if (city) {
      return city.cityNumber;
    } else {
      const randomNumber = Math.random();
      return Number(randomNumber.toFixed(2));
  
    }
  }
}