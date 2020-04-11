import { Component, OnInit, Input, SimpleChanges, OnChanges } from '@angular/core';
import { InterviewingCandidate, InterviewFeedback } from 'src/app/domain/interview';
import { InterviewService } from 'src/app/services/interview.service';

@Component({
  selector: 'hrms-feedback-display',
  templateUrl: './feedback-display.component.html',
  styleUrls: ['./feedback-display.component.scss']
})
export class FeedbackDisplayComponent implements OnInit, OnChanges {
  @Input() candidate: InterviewingCandidate;
  interviewFeedback: InterviewFeedback[];
  constructor(private interviewService: InterviewService) { }

  ngOnInit() {
  }
  
  ngOnChanges(changes: SimpleChanges): void {
    this.fetchFeedback();
  }

  fetchFeedback() {
    this.interviewService.getInterviewFeedback(this.candidate.id)
      .subscribe(feedback => this.interviewFeedback = feedback);
  }
  
}
