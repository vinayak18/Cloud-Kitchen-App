import { NgModule } from '@angular/core';
import { AccountComponent } from './account.component';
import { ProfileComponent } from './profile/profile.component';
import { AccountRoutingModule } from './account-routing.module';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PaymentComponent } from './payment/payment.component';
import { AddressComponent } from './address/address.component';

@NgModule({
  declarations: [
    AccountComponent,
    ProfileComponent,
    AddressComponent,
    PaymentComponent,
  ],
  imports: [
    AccountRoutingModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
  ],
  providers: [],
  bootstrap: [AccountComponent],
})
export class AccountModule {}
