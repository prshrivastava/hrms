import { Injectable } from '@angular/core';
import { PositionView, Position } from '../domain/position';
import { HttpClient } from '@angular/common/http';
import { Observable, of, from } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PositionService } from './position.service';
import { ResumeService } from '../resume/resume.service';
import { map, tap, concatAll, mergeAll, toArray, mergeMap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class PositionViewService {
    private apiUrl = environment.apiUrl;
    constructor(private http: HttpClient, private positionService: PositionService,
                private resumeService: ResumeService) {
    }

    public getPositionView(): Observable<PositionView[]> {
        return this.positionService.getPositions().pipe(
            //tap(p => console.log(p)),
            mergeMap(p => this.getMergedActiveApplicants(p))
        );
    }

    private getMergedActiveApplicants(positions: Position[]): Observable<PositionView[]> {
        return from(positions).pipe(
            map(p => this.getActiveapplicantCount(p)),
            mergeAll()
        ).pipe(toArray());
    }

    private getActiveapplicantCount(position: Position): Observable<PositionView> {
        return this.resumeService.getActiveApplicants(position.id).pipe(
            map(candidates => ({
                ...position,
                appliedCount: "" + candidates.filter(cand => cand.state=="APPLIED").length,
                shortListCount: "" + candidates.filter(cand => cand.state=="SHORTLISTED").length,
                interviewingCount: "" + candidates.filter(cand => cand.state=="INTERVIEWING").length,
                resumeRejectedCount: "" + candidates.filter(cand => cand.state=="RESUME_REJECTED").length
            })
            )
        );
    }
}