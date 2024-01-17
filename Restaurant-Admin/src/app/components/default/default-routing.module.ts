import { DefaultComponent } from './default.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { Error404Component } from './errors/error404/error404.component';
import { UnderMaintenanceComponent } from './errors/under-maintenance/under-maintenance.component';

const routes: Routes = [
  {
    path: '',
    component: DefaultComponent,
    children: [
      {
        path: 'login',
        component: SignInComponent,
      },
      {
        path: 'register',
        component: SignUpComponent,
      },
      {
        path: 'error',
        component: Error404Component,
      },
      {
        path: 'under-maintenance',
        component: UnderMaintenanceComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DefaultRoutingModule { }
