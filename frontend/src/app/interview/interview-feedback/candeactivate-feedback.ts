import { Injectable } from '@angular/core';
import { CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { InterviewFeedbackComponent } from './interview-feedback.component';

@Injectable({
    providedIn: 'root'
})
export class CanDeactivateInterviewFeedback implements CanDeactivate<InterviewFeedbackComponent> {
    canDeactivate(component: InterviewFeedbackComponent, currentRoute: ActivatedRouteSnapshot,
                  currentState: RouterStateSnapshot, nextState?: RouterStateSnapshot) {
        if (!component.canDeactivate()) {
            return confirm('Are you sure you want to discard changes?');
        }
        else {
            return true;
        }
    }

}