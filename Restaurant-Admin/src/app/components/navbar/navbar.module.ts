import { NgModule } from '@angular/core';
import { NavbarComponent } from './navbar.component';
import { NavbarRoutingModule } from './navbar-routing.module';
import { OrderComponent } from './order/order.component';
import { MenuComponent } from './menu/menu.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { CustomerComponent } from './customer/customer.component';
import { TeamComponent } from './team/team.component';
import { HeaderComponent } from './header/header.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReviewComponent } from './review/review.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { SharedModule } from 'src/app/shared.module';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { MenuItemComponent } from './menu/menu-item/menu-item.component';
import { OrderItemComponent } from './order/order-item/order-item.component';
import { DialogComponent } from './shared/dialog/dialog.component';
import { DialogService } from 'src/app/services/common/dialog.service';
@NgModule({
  declarations: [
    NavbarComponent,
    OrderComponent,
    MenuComponent,
    DashboardComponent,
    CustomerComponent,
    TeamComponent,
    HeaderComponent,
    ReviewComponent,
    FeedbackComponent,
    UserProfileComponent,
    MenuItemComponent,
    OrderItemComponent,
    DialogComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    NavbarRoutingModule,
    RouterModule,
    MatIconModule,
    MatSlideToggleModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule, MatMenuModule,
    SharedModule
  ],
  providers: [
    DialogService
  ]
})
export class NavbarModule { }