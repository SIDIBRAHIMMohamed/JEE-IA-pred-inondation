import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  constructor(private router: Router) { }

  hasToken!: boolean;

  ngOnInit(): void { 
    const token = localStorage.getItem('auth-token');
    this.hasToken = !!token;
  }


  logout(): void {
    localStorage.removeItem('auth-token');
    this.router.navigate(['login']);
  }

}
