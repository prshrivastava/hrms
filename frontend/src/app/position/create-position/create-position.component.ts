import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Position } from '../../domain/position';
import { PositionService } from '../../services/position.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-create-position',
  templateUrl: './create-position.component-reactive.html',
  styleUrls: ['./create-position.component.scss']
})
export class CreatePositionComponent implements OnInit {

  position: Position;
  positionForm: FormGroup;
  private canDiscard: boolean;
  editMode: boolean;
  constructor(private positionService: PositionService, private router: Router, private route: ActivatedRoute,
              private fb: FormBuilder) {
      if(this.route.snapshot.data['position']){
        this.editMode = true;
        this.position = this.route.snapshot.data['position'];
      }
      else {
        this.editMode = false;
        this.position = new Position(''); 
        //this.position.id = '0';
      }
      this.createForm();
  }
  @Output() created: EventEmitter<any> = new EventEmitter();
  ngOnInit() {
    this.canDiscard = false;
  }
  createForm(){
    this.positionForm = this.fb.group({
      name: '',
      skills: '',
      experienceRange: '',
      hiringManager: '',
      priority: ''
    });
    this.positionForm.patchValue(this.position);
  }
  onSubmit() {
    this.updateFromForm();
    this.positionService.createPosition(this.position).subscribe(position => {
      this.canDiscard = true;
      this.created.emit(position);
    });
    
  }

  cancel() {
    console.log('Cancel button clicked');
    this.canDiscard = true;
    this.created.emit();
  }

  public canDeactivate(): boolean {
    if(this.positionForm.pristine)
      return true;
    return this.canDiscard;
  }

  private updateFromForm() {
    const formModel = this.positionForm.value;
    this.position.name = formModel.name;
    this.position.skills = formModel.skills;
    this.position.experienceRange = formModel.experienceRange;
    this.position.hiringManager = formModel.hiringManager;
    this.position.priority = formModel.priority;
  }

}
