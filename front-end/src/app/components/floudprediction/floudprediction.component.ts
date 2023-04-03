import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {Chart, registerables} from 'chart.js';
@Component({
  selector: 'app-floudprediction',
  templateUrl: './floudprediction.component.html',
  styleUrls: ['./floudprediction.component.css']
})
export class FloudpredictionComponent {
  @ViewChild('myChart') canvas!: ElementRef;
  chart: any;
  title = 'angular-chart';
  constructor() { }

  // Chart.register(...registerables);
  ngOnInit(): void {
    // // Line Chart
    // const lineCanvasEle: any = document.getElementById('line_chart')
    // const lineChar = new Chart(lineCanvasEle.getContext('2d'), {
    //   type: 'line',
    //     data: {
    //       labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    //       datasets: [
    //         { data: [12, 15, 18, 14, 11, 19, 12], label: 'Orders', borderColor: 'rgba(54, 162, 235)' },
    //         { data: [65, 59, 80, 81, 56, 55, 40], label: 'Sales', borderColor: 'rgb(75, 192, 192)' },
    //       ],
    //     },
    //     options: {
    //       responsive: true,
    //       scales: {
    //           y: {
    //               beginAtZero: true
    //           }
    //       }
    //     }
    //   });
    // // Bar chart
    // const barCanvasEle: any = document.getElementById('bar_chart')
    // const barChart = new Chart(barCanvasEle.getContext('2d'), {
    //   type: 'bar',
    //     data: {
    //       labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    //       datasets: [{
    //         label: 'Sales',
    //         data: [65, 59, 80, 81, 56, 55, 40],
    //         backgroundColor: [
    //           'rgba(255, 99, 132, 0.2)',
    //           'rgba(255, 159, 64, 0.2)',
    //           'rgba(255, 205, 86, 0.2)',
    //           'rgba(75, 192, 192, 0.2)',
    //           'rgba(54, 162, 235, 0.2)',
    //           'rgba(153, 102, 255, 0.2)',
    //           'rgba(201, 203, 207, 0.2)'
    //         ],
    //         borderColor: [
    //           'rgb(255, 99, 132)',
    //           'rgb(255, 159, 64)',
    //           'rgb(255, 205, 86)',
    //           'rgb(75, 192, 192)',
    //           'rgb(54, 162, 235)',
    //           'rgb(153, 102, 255)',
    //           'rgb(201, 203, 207)'
    //         ],
    //         borderWidth: 1
    //       }]
    //     },
    //     options: {
    //       responsive: true,
    //       scales: {
    //           y: {
    //               beginAtZero: true
    //           }
    //       }
    //     }
    //   });
  // }
// }

//   ngOnInit(): void {
//     // Call the method to create the chart
//     this.createChart();
//   }

  // createChart(): void {
  //   // Define the data for the chart
  //   const data = {
  //     labels: ['Zone 1', 'Zone 2', 'Zone 3', 'Zone 4', 'Zone 5'],
  //     datasets: [{
  //       label: 'Inondation Prediction',
  //       data: [-3.44, 1.23, 0.58, -2.17, 0.87],
  //       backgroundColor: 'rgba(255, 99, 132, 0.2)',
  //       borderColor: 'rgba(255, 99, 132, 1)',
  //       borderWidth: 1
  //     }]
  //   };

    // Define the options for the chart
    // const options = {
    //   scales: {
    //     yAxes: [{
    //       ticks: {
    //         beginAtZero: true
    //       }
    //     }]
    //   }
    // };

    // Create the chart using Chart.js
    // const ctx = this.canvas.nativeElement.getContext('2d');
    // const ctx = document.getElementById('myChart');
    // this.chart = new Chart(ctx, {
      // type: 'bar',
      // data: data,
      // options: options
    // });
  }
}
