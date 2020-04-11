import { Injectable } from '@angular/core';
import { CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { CreatePositionComponent } from './create-position.component';

@Injectable()
export class CanDeactivateCreatePosition implements CanDeactivate<CreatePositionComponent> {
    canDeactivate(component: CreatePositionComponent, currentRoute: ActivatedRouteSnapshot,
                  currentState: RouterStateSnapshot, nextState?: RouterStateSnapshot): boolean  {
        if(!component.canDeactivate()){
            return confirm('Are you sure you want to discard changes?');
        }
        else {
            return true;
        }
    }

}
