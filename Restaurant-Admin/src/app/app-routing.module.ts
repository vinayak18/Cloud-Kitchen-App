import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Error404Component } from './components/default/errors/error404/error404.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: '',
    loadChildren: () =>
      import('./components/default/default.module').then((m) => m.DefaultModule),
  },
  {
    path: '',
    loadChildren: () =>
      import('./components/navbar/navbar.module').then((n) => n.NavbarModule),
  },
  {
    path: '**',
    component: Error404Component,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
