import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IonicModule } from '@ionic/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import { MatStepperModule } from '@angular/material/stepper';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import {
  SocialLoginModule,
  SocialAuthServiceConfig,
} from '@abacritt/angularx-social-login';
import { GoogleSigninButtonModule } from '@abacritt/angularx-social-login';
import {
  GoogleLoginProvider,
  FacebookLoginProvider,
  AmazonLoginProvider,
} from '@abacritt/angularx-social-login';
import { CoolSocialLoginButtonsModule } from '@angular-cool/social-login-buttons';
import { ToastrModule } from 'ngx-toastr';
import { HomeComponent } from './home/home.component';
import { CartComponent } from './cart/cart.component';
import { SpecialDishesComponent } from './special-dishes/special-dishes.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { AboutComponent } from './about/about.component';
import { MenuComponent } from './menu/menu.component';
import { LoginComponent } from './login/login.component';
import { OrderDetailsComponent } from './order/order-details/order-details.component';
import { TeamComponent } from './team/team.component';
import { ReviewComponent } from './review/review.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { ProductDetailsComponent } from './menu/product-details/product-details.component';
import { ActiveOrderComponent } from './order/active-order/active-order.component';
import { PastOrderComponent } from './order/past-order/past-order.component';
import { CheckoutComponent } from './order/checkout/checkout.component';
import { ScreenLoaderComponent } from '../screen-loader/screen-loader.component';
import { OrderConfirmationComponent } from './order/order-confirmation/order-confirmation.component';
import { ForgetPasswordComponent } from './login/forget-password/forget-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { DefaultRoutingModule } from './default-routing.module';
import { NgbCarouselModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpHeaderInterceptor } from 'src/app/interceptors/http-header.interceptor';
import { ScreenLoaderInterceptor } from 'src/app/interceptors/screen-loader.interceptor';
import { HttpErrorInterceptor } from 'src/app/interceptors/http-error.interceptor';
import { DefaultComponent } from './default.component';

const fbLoginOptions = {
  scope: 'public_profile',
  locale: 'en_US',
  return_scopes: true,
  enable_profile_selector: true,
  version: 'v13.0',
};

@NgModule({
  declarations: [
    DefaultComponent,
    HomeComponent,
    CartComponent,
    SpecialDishesComponent,
    HeaderComponent,
    FooterComponent,
    AboutComponent,
    MenuComponent,
    LoginComponent,
    OrderDetailsComponent,
    TeamComponent,
    ReviewComponent,
    FeedbackComponent,
    ProductDetailsComponent,
    ActiveOrderComponent,
    PastOrderComponent,
    CheckoutComponent,
    ScreenLoaderComponent,
    OrderConfirmationComponent,
    ForgetPasswordComponent,
    ChangePasswordComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    DefaultRoutingModule,
    RouterModule,
    MatMenuModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    BrowserAnimationsModule,
    IonicModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    NgbModule,
    NgbCarouselModule,
    CoolSocialLoginButtonsModule,
    SocialLoginModule,
    GoogleSigninButtonModule,
    ToastrModule.forRoot({
      positionClass: 'toast-position-custom',
      preventDuplicates: true,
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpHeaderInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ScreenLoaderInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider('<Google OAuth 2.0 Client ID>'),
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider(
              '<Facebook App ID>',
              fbLoginOptions
            ),
          },
        ],
        onError: (error) => {
          console.error(error);
        },
      } as SocialAuthServiceConfig,
    },
  ],
})
export class DefaultModule {}
