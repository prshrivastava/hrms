import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, from, of } from 'rxjs';
import { Interview, InterviewFeedback } from '../domain/interview';
import { environment } from 'src/environments/environment';
import { Candidate } from '../domain/candidate';
import { InterviewingCandidate } from '../domain/interview';
import { mergeMap, map, toArray, mergeAll } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class InterviewService {
  private apiUrl = environment.apiUrl + '/interview';
  constructor(private http: HttpClient) { }

  public getInterviews(positionId: string, candidateId: string): Observable<Interview[]> {
    return this.http.get<Interview[]>(this.apiUrl + '/schedule/' + positionId + '/' + candidateId);
  }
  public getCandidateWithInterviews(positionId: string, candidate: Candidate): Observable<InterviewingCandidate> {
    return this.getInterviews(positionId, candidate.id).pipe(
      map(interviews => ({ ...candidate, interviews }))
    );
  }
  public scheduleInterview(positionId: string, candidateId: string, interview: Interview) {
    return this.http.post(this.apiUrl + '/schedule/' + positionId + '/' + candidateId, interview);
  }

  public getInterviewingCandidates(positionId: string): Observable<Candidate[]> {
    return this.http.get<Candidate[]>(this.apiUrl + '/candidates/' + positionId);
  }

  public getCandidatesWithInterviews(positionId: string): Observable<InterviewingCandidate[]> {
    return this.getInterviewingCandidates(positionId).pipe(
      mergeMap(candidates => this.getMergedCandidatesWithInterviews(positionId, candidates))
    );
  }

  public submitFeedback(interviewId: string, comment: string) {
    return this.http.post(this.apiUrl + '/feedback/' + interviewId, comment);
  }

  public getInterviewFeedback(candidateId: string): Observable<InterviewFeedback[]> {
    return this.http.get<InterviewFeedback[]>(this.apiUrl + '/feedback/' + candidateId);
  }

  private getMergedCandidatesWithInterviews(positionId: string, candidates: Candidate[]): Observable<InterviewingCandidate[]> {
    return from(candidates).pipe(
      mergeMap(c => this.getCandidateWithInterviews(positionId, c)),
    ).pipe(toArray());
  }

}
