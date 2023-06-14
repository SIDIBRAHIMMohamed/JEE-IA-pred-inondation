import { Component,OnInit } from '@angular/core';
import { Ville } from 'app/models/ville';
import { VilleService } from 'app/services/ville.service';

@Component({
  selector: 'app-visualiser',
  templateUrl: './visualiser.component.html',
  styleUrls: ['./visualiser.component.css']
})
export class VisualiserComponent implements OnInit {
  villes!: Ville[];
  filterDate!: string;

  constructor(private villeService: VilleService) { }

  ngOnInit() {
    this.villeService.getLastPredictions().subscribe(
      data => this.villes = data,
      error => console.log(error)
    );
  }

  filterData() {
    this.villeService.filterByDate(this.filterDate).subscribe(
      data => this.villes = data,
      error => console.log(error)
    );
  }

}
