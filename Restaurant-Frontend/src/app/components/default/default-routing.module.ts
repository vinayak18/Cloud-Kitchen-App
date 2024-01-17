import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DefaultComponent } from './default.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AboutComponent } from './about/about.component';
import { CartComponent } from './cart/cart.component';
import { SpecialDishesComponent } from './special-dishes/special-dishes.component';
import { MenuComponent } from './menu/menu.component';
import { ProductDetailsComponent } from './menu/product-details/product-details.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ForgetPasswordComponent } from './login/forget-password/forget-password.component';
import { CheckoutComponent } from './order/checkout/checkout.component';
import { ActiveOrderComponent } from './order/active-order/active-order.component';
import { PastOrderComponent } from './order/past-order/past-order.component';
import { OrderDetailsComponent } from './order/order-details/order-details.component';
import { OrderConfirmationComponent } from './order/order-confirmation/order-confirmation.component';
import { AuthGuard } from 'src/app/guard/auth.guard';
const routes: Routes = [
  {
    path: '',
    component: DefaultComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full',
      },
      {
        path: 'account/:id',
        loadChildren: () =>
          import(`./account/account.module`).then((m) => m.AccountModule), //lazy loading
      },
      {
        path: 'home',
        component: HomeComponent,
      },
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'about',
        component: AboutComponent,
      },
      {
        path: 'cart',
        component: CartComponent,
      },
      {
        path: 'cart/checkout',
        component: CheckoutComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'order/active',
        component: ActiveOrderComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'order/past',
        component: PastOrderComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'order/active/details/:orderId',
        component: OrderDetailsComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'order/past/details/:orderId',
        component: OrderDetailsComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'order/confirmation',
        component: OrderConfirmationComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'special-dishes',
        component: SpecialDishesComponent,
      },
      {
        path: 'menu',
        component: MenuComponent,
      },
      {
        path: 'menu/product/:productId',
        component: ProductDetailsComponent,
      },
      {
        path: 'change-password',
        component: ChangePasswordComponent,
      },
      {
        path: 'forget-password',
        component: ForgetPasswordComponent,
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DefaultRoutingModule {}
