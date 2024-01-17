import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IonicModule } from '@ionic/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgbCarouselModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatStepperModule } from '@angular/material/stepper';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CoolSocialLoginButtonsModule } from '@angular-cool/social-login-buttons';
import { ToastrModule } from 'ngx-toastr';
import { HttpHeaderInterceptor } from './interceptors/http-header.interceptor';
import { HttpErrorInterceptor } from './interceptors/http-error.interceptor';
import { ScreenLoaderInterceptor } from './interceptors/screen-loader.interceptor';
import { DefaultModule } from './components/default/default.module';
import { ErrorModule } from './components/errors/error.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    DefaultModule,
    ErrorModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
