import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InterviewingCandidate, Interview } from 'src/app/domain/interview';
import { InterviewService } from 'src/app/services/interview.service';
import { Position } from 'src/app/domain/position';
import { FormGroup, FormBuilder } from '@angular/forms';
import { FeedbackDisplayComponent } from '../feedback-display/feedback-display.component';

@Component({
  selector: 'app-interview-feedback',
  templateUrl: './interview-feedback.component.html',
  styleUrls: ['./interview-feedback.component.scss']
})
export class InterviewFeedbackComponent implements OnInit {
  position: Position;
  activeCandidates: InterviewingCandidate[];
  showFeedbackForCandidate: InterviewingCandidate;
  submitFeedbackFor: Interview;
  feedbackForm: FormGroup;
  @ViewChild(FeedbackDisplayComponent, {static: false}) feedbackDisplayComponent: FeedbackDisplayComponent;

  constructor(private router: Router, private route: ActivatedRoute,
    private interviewService: InterviewService, private fb: FormBuilder) { }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.position = data['position'];
      if (this.position.id != "0") {
        this.fetchInterviewingCandidates();
        this.showFeedbackForCandidate = null;
        this.submitFeedbackFor = null;
        if (this.feedbackForm) {
          this.feedbackForm.reset();
        }
      }
    });
    this.feedbackForm = this.fb.group({
      comment: ''
    });
  }

  public selectCandidate(candidate: InterviewingCandidate) {
    if (candidate == this.showFeedbackForCandidate) return;
    if (this.discardFeedbackForm()) {
      this.showFeedbackForCandidate = candidate;
      this.submitFeedbackFor = null;
      this.feedbackForm.reset();
    }
  }

  public selectForSubmittingFeedback(candidate: InterviewingCandidate, interview: Interview) {
    if (interview == this.submitFeedbackFor) return;
    if (this.discardFeedbackForm()) {
      this.showFeedbackForCandidate = candidate;
      this.submitFeedbackFor = interview;
      this.feedbackForm.reset();
    }
  }
  private discardFeedbackForm() {
    if (this.feedbackForm.dirty) {
      return confirm('Do you want to discard?');
    }
    return true;
  }

  public canDeactivate() {
    return !this.feedbackForm.dirty;
  }
  onSubmit() {
    console.log(this.feedbackForm.value.comment);
    this.interviewService.submitFeedback(this.submitFeedbackFor.id, this.feedbackForm.value.comment).subscribe(() => {
      this.cancel();
      this.feedbackDisplayComponent.fetchFeedback();
    });
  }

  cancel() {
    this.submitFeedbackFor = null;
    this.feedbackForm.reset();
  }
  private fetchInterviewingCandidates() {
    this.interviewService.getCandidatesWithInterviews(this.position.id).subscribe(candidates => {
      this.activeCandidates = candidates;
    });
  }
}
