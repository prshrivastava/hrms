import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Position } from '../domain/position';
import { Candidate } from '../domain/candidate';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ResumeService {
  private apiUrl = environment.apiUrl+'/candidate';
  private applicants = {};
  constructor(private http: HttpClient) {
  }

  saveResumes(positionId: string, resumes: Candidate[]) {
    return this.http.post(this.apiUrl + '/apply/' + positionId, resumes);
  }
   
  getUprocessedCandidates(positionId: string): Observable<Candidate[]> {
    return this.http.get<Candidate[]>(this.apiUrl + '/applied/' + positionId);
  }
  getShortlistedCandidates(positionId: string): Observable<Candidate[]> {
    return this.http.get<Candidate[]>(this.apiUrl + '/shortlisted/' + positionId);
  }
  getRejectedCandidates(positionId: string): Observable<Candidate[]> {
    return this.http.get<Candidate[]>(this.apiUrl + '/rejected/' + positionId);
  }
  markShortlisted(positionId: string, candidateIds: string[]) {
    return this.http.post(this.apiUrl + '/shortlist/' + positionId, candidateIds);
  }
  markRejected(positionId: string, candidateIds: string[]) {
    return this.http.post(this.apiUrl + '/reject/' + positionId, candidateIds);
  }
  getActiveApplicants(positionId: string): Observable<Candidate[]> {
    return this.http.get<Candidate[]>(this.apiUrl + '/active/' + positionId);
  }
}


