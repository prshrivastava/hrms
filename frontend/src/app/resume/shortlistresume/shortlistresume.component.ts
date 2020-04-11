import { Component, OnInit } from '@angular/core';
import { ResumeService } from '../resume.service';
import { Candidate } from '../../domain/candidate';
import { PositionService } from 'src/app/services/position.service';
import { Position } from 'src/app/domain/position';
import { Router, ActivatedRoute } from '@angular/router';
import { forkJoin, combineLatest } from 'rxjs';
import { InterviewService } from 'src/app/services/interview.service';

@Component({
  selector: 'app-shortlistresume',
  templateUrl: './shortlistresume.component.html',
  styleUrls: ['./shortlistresume.component.scss']
})
export class ShortlistresumeComponent implements OnInit {
  position: Position;
  private dirty: boolean;
  candidates: CandidateView[];
  private candidateSortMap = { APPLIED: 0, SHORTLISTED: 1, RESUME_REJECTED: 2 };

  constructor(private resumeService: ResumeService, private positionService: PositionService, private interviewService: InterviewService,
    private router: Router, private route: ActivatedRoute) {
    this.dirty = false;
    this.candidates = [];
  }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.position = data['position'];
      if (this.position.id != "0") {
        this.dirty = false;
        this.fetchActiveCandidates();
      }
    });
  }

  private fetchActiveCandidates() {
    this.resumeService.getActiveApplicants(this.position.id).subscribe(applicants => {
      this.candidates = applicants.filter(a => a.state != 'INTERVIEWING')
        .map(app => ({ ...app, dirty: false, newState: app.state }))
        .sort((a, b) => this.candidateSortMap[a.state] - this.candidateSortMap[b.state]);
    });
  }

  onShortlist(candidate: CandidateView) {
    if (!this.enableShortListAction(candidate)) { return; }
    this.dirty = true;
    candidate.dirty = true;
    candidate.newState = 'SHORTLISTED';

  }

  onReject(candidate: CandidateView) {
    if (!this.enableRejectAction(candidate)) { return; }
    this.dirty = true;
    candidate.dirty = true;
    candidate.newState = 'RESUME_REJECTED';
  }

  onSubmit() {
    const shortListedCandidates = this.candidates.filter(c => c.dirty == true && c.newState == 'SHORTLISTED');
    const shortListedCId: string[] = shortListedCandidates.map(c => c.id);

    const rejectedCandidates = this.candidates.filter(c => c.dirty == true && c.newState == 'RESUME_REJECTED');
    const rejectedCId: string[] = rejectedCandidates.map(c => c.id);

    //console.log(shortListedCId, rejectedCId);
    forkJoin([
      this.resumeService.markShortlisted(this.position.id, shortListedCId),
      this.resumeService.markRejected(this.position.id, rejectedCId)
    ]).subscribe(results => {
      this.fetchActiveCandidates();
      this.dirty = false;
    });
  }
  undo(candidate: CandidateView) {
    candidate.dirty = false;
    candidate.newState = candidate.state;
  }

  canDeactivate() {
    return !this.dirty;
  }
  enableShortListAction(candidate: CandidateView) {
    return candidate.state != 'SHORTLISTED' || candidate.newState != 'SHORTLISTED';
  }
  enableRejectAction(candidate: CandidateView) {
    return candidate.state != 'RESUME_REJECTED' || candidate.newState != 'RESUME_REJECTED';
  }

}
enum ShowResume {
  UNPROCESSED, SHORTLISTED, REJECTED
}

class CandidateView extends Candidate {
  dirty: boolean;
  newState: string;
}