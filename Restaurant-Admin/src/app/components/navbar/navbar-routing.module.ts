import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavbarComponent } from './navbar.component';
import { OrderComponent } from './order/order.component';
import { MenuComponent } from './menu/menu.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CustomerComponent } from './customer/customer.component';
import { TeamComponent } from './team/team.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { ReviewComponent } from './review/review.component';
import { OrderItemComponent } from './order/order-item/order-item.component';
import { MenuItemComponent } from './menu/menu-item/menu-item.component';

const routes: Routes = [
    {
        path: '',
        component: NavbarComponent,
        children: [
            {
                path: '',
                redirectTo: '/dashboard',
                pathMatch: 'full',
            },
            {
                path: 'dashboard',
                component: DashboardComponent,
            },
            {
                path: 'order/:status',
                component: OrderComponent,
            },
            {
                path: 'order/:status/:orderid',
                component: OrderItemComponent,
            },
            {
                path: 'menu/:foodtype',
                component: MenuComponent,
            },
            {
                path: 'menu/:foodtype/:pid',
                component: MenuItemComponent,
            },
            {
                path: 'customer',
                component: CustomerComponent,
            },
            {
                path: 'restaurant/team',
                component: TeamComponent,
            },
            {
                path: 'restaurant/feedback',
                component: FeedbackComponent,
            },
            {
                path: 'product/review',
                component: ReviewComponent,
            },
        ],
    },
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class NavbarRoutingModule { }
