import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DefaultComponent } from './default.component';
import { DefaultRoutingModule } from './default-routing.module';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { Error404Component } from './errors/error404/error404.component';
import { UnderMaintenanceComponent } from './errors/under-maintenance/under-maintenance.component';

import { SharedModule } from 'src/app/shared.module';

@NgModule({
  declarations: [
    DefaultComponent,
    SignInComponent,
    SignUpComponent,
    Error404Component,
    UnderMaintenanceComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    DefaultRoutingModule,
    SharedModule
  ],
  providers: [

  ],
})
export class DefaultModule { }
