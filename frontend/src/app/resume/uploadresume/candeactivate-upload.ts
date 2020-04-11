import { Injectable } from "@angular/core";
import { CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UploadresumeComponent } from './uploadresume.component';

@Injectable({
    providedIn: 'root'
})
export class CanDeactivateUpload implements CanDeactivate<UploadresumeComponent> {
    canDeactivate(component: UploadresumeComponent, currentRoute: ActivatedRouteSnapshot,
        currentState: RouterStateSnapshot, nextState?: RouterStateSnapshot) {
        if (!component.canDeactivate()) {
            return confirm('Are you sure you want to discard changes?');
        }
        else {
            return true;
        }
    }

}