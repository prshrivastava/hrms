import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { PositionService } from '../services/position.service';
import { Position } from '../domain/position';
import { Observable, of } from 'rxjs';

@Injectable()
export class PositionResolver implements Resolve<Position> {

    constructor(private positionService: PositionService) { }
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Position> {
        if (route.params.id === '0') {
            return of(new Position());
        }
        return this.positionService.getPostion(route.params.id);
    }

}
