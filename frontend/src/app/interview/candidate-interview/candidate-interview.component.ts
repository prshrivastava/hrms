import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { InterviewService } from '../../services/interview.service';
import { Position } from '../../domain/position';
import moment from 'moment';
import { Interview, InterviewingCandidate} from '../../domain/interview';

@Component({
  selector: 'candidate-interview',
  templateUrl: './candidate-interview.component.html',
  styleUrls: ['./candidate-interview.component.scss'],
  animations: [ //Not in use
    trigger('expandCollapse', [
      state('expand', style({  height: '100px'})),
      state('collapse', style({ height: '3px'})),
      transition('expand <=> collapse', [animate('0.1s')])
    ])
  ]
})
export class CandidateInterviewComponent implements OnInit {
  state: string;
  scheduleForm: FormGroup;
  submitted: boolean;
  constructor(private fb: FormBuilder, private interviewService: InterviewService) { 
    this.state ='collapse';
  }

  @Input() candidate: InterviewingCandidate;
  @Input() position: Position;
  ngOnInit() {
    this.createForm();
    this.submitted = false;
  }
  private createForm(){
    this.scheduleForm = this.fb.group({
      date: ['', Validators.required],
      time: ['', Validators.required],
      interviewers: '',
      candidateId: this.candidate.id
    });
  }
  toggle(){
    if(this.state == 'collapse') {
      this.state = 'expand';
      this.fetchInterviews();
    }
    else {
      this.state = 'collapse';
    }
  }
  private fetchInterviews() {
    this.interviewService.getInterviews(this.position.id, this.candidate.id).subscribe(
      interviews => this.candidate.interviews = interviews.map(i => { return { ...i, slot: moment(i.slot, "YYYY-MM-DDTHH:mm:ss.SSSZ").toDate() }; }));
  }

  scheduleInterview() {
    this.submitted = true;
    if(this.scheduleForm.valid) {
      const interview: Interview = this.mapToInterview(this.scheduleForm.value);
      this.interviewService.scheduleInterview(this.position.id, this.candidate.id, interview).subscribe(()=> {
        this.cancel();
        this.fetchInterviews();
      });
    }
  }
  cancel() {
    this.scheduleForm.reset();
    this.submitted = false;
  }

  private mapToInterview(formValue): Interview {
    let i:Interview = new Interview();
    i.panel = formValue.interviewers;
    i.slot = moment(formValue.date+' '+formValue.time, "DD/MM/YY HH:mm").toDate();
    console.log(i.slot);
    console.log(JSON.stringify(i.slot));
    return i;
  }

}
