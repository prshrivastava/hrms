import { Injectable } from "@angular/core";
import { CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { ShortlistresumeComponent } from './shortlistresume.component';

@Injectable({
    providedIn: 'root'
})
export class CanDeactivateShortlist implements CanDeactivate<ShortlistresumeComponent> {
    canDeactivate(component: ShortlistresumeComponent, currentRoute: ActivatedRouteSnapshot,
        currentState: RouterStateSnapshot, nextState?: RouterStateSnapshot) {
        if (!component.canDeactivate()) {
            return confirm('Are you sure you want to discard changes?');
        }
        else {
            return true;
        }
    }

}