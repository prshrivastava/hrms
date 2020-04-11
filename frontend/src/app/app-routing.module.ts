import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreatePositionComponent } from './position/create-position/create-position.component';
import { PositionComponent } from './position/position/position.component';
import { ListPositionComponent } from './position/list-position/list-position.component';


const routes: Routes = [
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
