import { Component, OnInit } from '@angular/core';
import { PositionService } from 'src/app/services/position.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-resume',
  templateUrl: './positionselection.component.html',
  styleUrls: ['./positionselection.component.scss']
})
export class PositionSelectionComponent implements OnInit {
  positions$ = this.positionService.getPositions();
  selectedPositionId;
  title: string;
  constructor(private positionService: PositionService, private router: Router, private route: ActivatedRoute) {
    this.title = this.route.snapshot.data['title'];
  }

  ngOnInit() {

  }
  
  selectPosition(position) {
    this.router.navigate([ position.id], { relativeTo: this.route }).then(
      value => {
        if(value)
          this.selectedPositionId = position.id;
      });
  }

}
