import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { PositionSelectionComponent } from '../resume/resume/positionselection.component';
import { InterviewScheduleComponent } from './interview-schedule/interview-schedule.component';
import { PositionResolver } from '../position/position.resolver';
import { CandidateInterviewComponent } from './candidate-interview/candidate-interview.component';
import { InterviewFeedbackComponent } from './interview-feedback/interview-feedback.component';
import { FeedbackDisplayComponent } from './feedback-display/feedback-display.component';
import { CanDeactivateInterviewFeedback } from './interview-feedback/candeactivate-feedback';


@NgModule({
  declarations: [InterviewScheduleComponent, CandidateInterviewComponent, InterviewFeedbackComponent, FeedbackDisplayComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    RouterModule.forChild([
      {
        path: 'schedule', component: PositionSelectionComponent, data: {title: 'Schedule Interview'},
        children: [
          { path: ':id', component: InterviewScheduleComponent, resolve: {position: PositionResolver}
          }
        ]
      },
      {
        path: 'feedback', component: PositionSelectionComponent, data: {title: 'Submit Feedback'},
        children: [
          { path: ':id', component: InterviewFeedbackComponent, resolve: {position: PositionResolver},
            canDeactivate: [CanDeactivateInterviewFeedback]
          }
        ]
      }
    ])
  ]
})
export class InterviewModule { }
