import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'hrms-app';
  constructor(private router: Router){}

  captureCreated($event){
    console.log('The position is created '+JSON.stringify($event));
  }
 
}
