import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { PositionService } from 'src/app/services/position.service';
import { Position } from 'src/app/domain/position';
import { ResumeService } from '../resume.service';
import { Candidate } from '../../domain/candidate';
import { CanDeactivateUpload } from './candeactivate-upload';

@Component({
  selector: 'app-uploadresume',
  templateUrl: './uploadresume.component.html',
  styleUrls: ['./uploadresume.component.scss']
})
export class UploadresumeComponent implements OnInit {

  position: Position;
  resumeForm: FormGroup;
  candidates: FormArray;
  private canDiscard: boolean;
  constructor(private router: Router, private route: ActivatedRoute,
    private fb: FormBuilder, private positionService: PositionService, private resumeService: ResumeService) {
      this.position = new Position();
  }

  ngOnInit() {
    this.candidates = this.fb.array([this.createResumeForm()]);
    this.resumeForm = this.fb.group({
      candidates: this.candidates
    });
    this.route.params.subscribe(params => {
      console.log(params);
      const posId = params['positionid'];
      if(posId !== "0")
        this.positionService.getPostion(posId).subscribe(p => this.position = p);
      else {
        this.position.id = '0';
      }
      this.initialize();
    });
  }
  addCandidate() {
    //this.resumeForm.get('candidates').push(this.createResumeForm());
    //let candFormArray: FormArray = this.resumeForm.get('candidates');
    //candFormArray.push(this.createResumeForm());
    this.candidates.push(this.createResumeForm());
  }
  
  createResumeForm(): FormGroup {
    return this.fb.group({
      name: '',
      resumeLink: '',
      referral:'',
      referredBy:''
    });
  }
  uploadResumes() {
    //let resumeData = this.resumeForm.value;
    //this.resumeService.saveResumes(this.position.id, resumeData.candidates);
    console.log(this.mapResumeFormToCandidateResume());
    this.resumeService.saveResumes(this.position.id, this.mapResumeFormToCandidateResume()).subscribe(
      data => {
        this.initialize();
      }
    );
  }

  initialize(){
    this.candidates.clear();
    this.addCandidate();
    this.resumeForm.reset();
  }
  mapResumeFormToCandidateResume(): Candidate[] {
    let candidateResume: Candidate[] = [];
    this.resumeForm.value.candidates.forEach(element => {
      let c = new Candidate(element.name, element.resumeLink);
      c.referral = element.referral || false;
      candidateResume.push(c);
    });
    return candidateResume;
  }
  canDeactivate(){
    return this.resumeForm.pristine;
  }
  cancel(){
    if(!this.canDeactivate() && confirm('Are you sure you want to discard changes?')){
      this.initialize();
    }
  }
}

