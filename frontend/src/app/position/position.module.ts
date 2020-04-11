import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ListPositionComponent } from './list-position/list-position.component';
import { CreatePositionComponent } from './create-position/create-position.component';
import { PositionService } from '../services/position.service';
import { RouterModule } from '@angular/router';
import { PositionComponent } from './position/position.component';
import { CanDeactivateCreatePosition } from './create-position/create-position-candeactivate';
import { PositionResolver } from './position.resolver';

@NgModule({
  declarations: [ListPositionComponent, CreatePositionComponent, PositionComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forChild([
      {
        path: 'position', component: PositionComponent,
        children: [
          { path: '', redirectTo: 'list', pathMatch: 'full' },
          { path: 'list', component: ListPositionComponent },
          { path: ':id/edit', component: CreatePositionComponent,
            canDeactivate: [CanDeactivateCreatePosition], resolve: {position: PositionResolver}
          }
        ]
      }
    ])
  ],
  exports: [
    ListPositionComponent,
    CreatePositionComponent
  ],
  providers: [
    PositionService, CanDeactivateCreatePosition, PositionResolver
  ]
})
export class PositionModule { }
