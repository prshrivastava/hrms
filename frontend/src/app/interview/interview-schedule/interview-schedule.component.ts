import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ResumeService } from 'src/app/resume/resume.service';
import { Candidate } from 'src/app/domain/candidate';
import { concatMap } from 'rxjs/operators';
import { Position } from 'src/app/domain/position';
import { CandidateInterviewComponent } from '../candidate-interview/candidate-interview.component';
import { Interview, InterviewingCandidate } from '../../domain/interview';
import { InterviewService } from '../../services/interview.service';

@Component({
  selector: 'app-interview-schedule',
  templateUrl: './interview-schedule.component.html',
  styleUrls: ['./interview-schedule.component.scss']
})
export class InterviewScheduleComponent implements OnInit {
  position: Position;
  activeCandidates: InterviewingCandidate[];

  constructor(private router: Router, private route: ActivatedRoute,
    private resumeService: ResumeService, private interviewService: InterviewService) {
  }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.position = data['position'];
      if (this.position.id != "0") {
        this.fetchActiveCandidates();
      }
    });
  }

  private fetchActiveCandidates() {
    this.resumeService.getActiveApplicants(this.position.id).subscribe(activecands => {
      const ic = activecands.filter(c => c.state == 'SHORTLISTED' || c.state == 'INTERVIEWING');
      this.activeCandidates = ic.map(value => {
        const c: InterviewingCandidate = { ...value, interviews: [] };
        return c;
      });
    });
  }
}
