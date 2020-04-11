import { Component, OnInit } from '@angular/core';
import {PositionView} from '../../domain/position';
import { PositionService } from '../../services/position.service';
import { ResumeService } from 'src/app/resume/resume.service';
import { PositionViewService } from 'src/app/services/positionview.service';


@Component({
  selector: 'list-position',
  templateUrl: './list-position.component.html',
  styleUrls: ['./list-position.component.scss']
})
export class ListPositionComponent implements OnInit {
  positions: PositionView[];
  constructor(private positionService: PositionService, private resumeService: ResumeService,
              private posViewService: PositionViewService) {
    
  }

  ngOnInit() {
    this.posViewService.getPositionView().subscribe(data => {
      this.positions = data.sort((d1,d2) => parseInt(d1.id) - parseInt(d2.id));
    });
  }

}

