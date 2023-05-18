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
  
  ngOnInit(): void { 
    const token = localStorage.getItem('auth-token');
    if (!token) { 
      this.router.navigate(['signup']);
    }
}
  selectedFile!: File ;
 
  constructor(private http: HttpClient,private router:Router){}
  
  onExcelFileSelected(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = () => {
      const data = reader.result;
      const workbook = XLSX.read(data, { type: 'binary' });
      const sheetName = workbook.SheetNames[0];
      const worksheet = workbook.Sheets[sheetName];
      const json = XLSX.utils.sheet_to_json(worksheet);
      console.log(json);
      // this.http.post('http://back-end URL', json).subscribe(() => {
      //   console.log('File uploaded successfully!');
      // });
    };
    }
    navigate():void{
      this.router.navigate(['visualiser']);
 
  }
  uploadExcelFile():void{
    console.log("I'm here!!!!");
  };
  
  
  

}
