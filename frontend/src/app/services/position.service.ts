import { Injectable } from '@angular/core';
import { Position } from '../domain/position';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PositionService {
  private positions: Position[] = [];
  private apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) {
  }

  getPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(this.apiUrl + '/positions');
  }
  getPostion(id: string): Observable<Position> {
    return this.http.get<Position>(this.apiUrl + '/positions/' + id);
  }
  createPosition(position: Position): Observable<Position> {
    if(position.id) {
      return this.http.put<Position>(this.apiUrl + '/positions', position);
    }
    return this.http.post<Position>(this.apiUrl + '/positions', position)
  }
}
