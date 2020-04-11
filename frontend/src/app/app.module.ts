import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { PositionModule } from './position/position.module';
import { ResumeModule } from './resume/resume.module';
import { InterviewModule } from './interview/interview.module';
import { HttpErrorInterceptor } from './http-error-interceptor';
import { ChartsModule } from './charts/charts.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    PositionModule,
    ResumeModule,
    InterviewModule,
    ChartsModule,
    AppRoutingModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpErrorInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
