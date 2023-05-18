import { Component, OnInit } from '@angular/core';
import * as XLSX from 'xlsx';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit{
  file: File | null = null;
  data: any[] | null = null;
  tokenjwt: string | null = null;

  ngOnInit(): void {
    const token = localStorage.getItem('auth-token');
    this.tokenjwt = token;
    if (!token) {
      this.router.navigate(['signup']);
    }
}


  constructor(private http: HttpClient,private router:Router){}

  // onExcelFileSelected(event: any): void {
  //   const file = event.target.files[0];
  //   const reader = new FileReader();
  //   reader.readAsBinaryString(file);
  //   reader.onload = () => {
  //     const data = reader.result;
  //     const workbook = XLSX.read(data, { type: 'binary' });
  //     const sheetName = workbook.SheetNames[0];
  //     const worksheet = workbook.Sheets[sheetName];
  //     const json = XLSX.utils.sheet_to_json(worksheet);
  //     console.log(json);
  //     // this.http.post('http://back-end URL', json).subscribe(() => {
  //     //   console.log('File uploaded successfully!');
  //     // });
  //   };
  //   }
  //   navigate():void{
  //     this.router.navigate(['visualiser']);

  // }
  // uploadExcelFile():void{
  //   console.log("I'm here!!!!");
  // };

  navigate():void{
        this.router.navigate(['visualiser']);
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  upload() {
    if (!this.file) {
      return;
    }
    const fileReader = new FileReader();
    fileReader.onload = (e) => {
      const arrayBuffer: any = e.target?.result;
      const data = new Uint8Array(arrayBuffer);
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetName = workbook.SheetNames[0];
      const worksheet = workbook.Sheets[sheetName];
      this.data = XLSX.utils.sheet_to_json(worksheet);
      this.sendData();
    };

    fileReader.readAsArrayBuffer(this.file);
  }

  sendData() {
    if (!this.data) {
      return;
    }
    const headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.tokenjwt
    };


    this.http.post('http://localhost:8082/api/predict', this.data, { headers }).subscribe({
      next: (res) => {
        this.router.navigate(['visualiser']);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
}
