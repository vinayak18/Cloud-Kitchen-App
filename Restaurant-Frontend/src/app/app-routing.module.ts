import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./components/default/default.module').then(
        (m) => m.DefaultModule
      ),
  },
  {
    path: '',
    loadChildren: () =>
      import('./components/errors/error.module').then(
        (m) => m.ErrorModule
      ),
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      onSameUrlNavigation: 'reload',
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
