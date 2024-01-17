import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnderMaintenanceComponent } from './under-maintenance/under-maintenance.component';
import { ErrorComponent } from './error.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
  {
    path: '',
    component: ErrorComponent,
    children: [
      {
        path: 'under-maintenance',
        component: UnderMaintenanceComponent
      },
      {
        path: '**',
        component: PageNotFoundComponent
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ErrorRoutingModule { }
