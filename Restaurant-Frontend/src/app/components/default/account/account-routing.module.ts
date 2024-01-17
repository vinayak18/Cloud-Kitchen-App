import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guard/auth.guard';
import { AccountComponent } from './account.component';
import { ProfileComponent } from './profile/profile.component';
import { PaymentComponent } from './payment/payment.component';
import { DeActiveGuard } from 'src/app/guard/de-active.guard';
import { AddressComponent } from './address/address.component';
const routes: Routes = [
  {
    path: '',
    component: AccountComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        redirectTo: 'profile',
        pathMatch: 'full',
      },
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'address',
        component: AddressComponent,
        canDeactivate: [DeActiveGuard],
      },
      {
        path: 'payment',
        component: PaymentComponent,
      },
    ],
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AccountRoutingModule {}
