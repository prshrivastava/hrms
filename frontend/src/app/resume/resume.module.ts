import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { PositionSelectionComponent } from './resume/positionselection.component';
import { UploadresumeComponent } from './uploadresume/uploadresume.component';
import { ShortlistresumeComponent } from './shortlistresume/shortlistresume.component';
import { PositionResolver } from '../position/position.resolver';
import { CanDeactivateUpload } from './uploadresume/candeactivate-upload';
import { CanDeactivateShortlist } from './shortlistresume/candeactivate-shortlist';


@NgModule({
  declarations: [PositionSelectionComponent, UploadresumeComponent, ShortlistresumeComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild([
      {
        path: 'screen', component: PositionSelectionComponent, data: {title: 'Screen Resumes'},
        children: [
          // { path: '', redirectTo: 'screen', pathMatch: 'full' },
          { path: ':id', component: ShortlistresumeComponent, resolve: {position: PositionResolver},
            canDeactivate: [CanDeactivateShortlist] }
        ]
      },
      {
        path: 'upload', component: PositionSelectionComponent, data: {title: 'Upload Resumes'},
        children: [
          { path: ':positionid', component: UploadresumeComponent,
          canDeactivate: [CanDeactivateUpload]
          }
        ]
      }
    ])
  ]
})
export class ResumeModule { }
