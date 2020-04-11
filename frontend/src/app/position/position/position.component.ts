import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CreatePositionComponent } from '../create-position/create-position.component';

@Component({
  selector: 'app-position',
  templateUrl: './position.component.html',
  styleUrls: ['./position.component.scss']
})
export class PositionComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
    console.log(this.router.getCurrentNavigation());
  }
  public onActivate(elementRef) {
    console.log(elementRef);
    if(elementRef instanceof CreatePositionComponent) {
      elementRef.created.subscribe(event =>
          {
            console.log('Now navigating to positions');
            console.log(this.router.getCurrentNavigation());
            this.router.navigate(['position']);
          }
        );
    }
  }

}
