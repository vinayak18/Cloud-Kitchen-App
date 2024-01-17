import { NgModule } from "@angular/core";
import { ScreenLoaderComponent } from "./components/screen-loader/screen-loader.component";
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule } from "@angular/material/dialog";
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
const fbLoginOptions = {
  scope: 'public_profile',
  locale: 'en_US',
  return_scopes: true,
  enable_profile_selector: true,
  version: 'v13.0',
};
@NgModule({
  declarations: [ScreenLoaderComponent],
  exports: [ScreenLoaderComponent, MatDialogModule,
    SocialLoginModule,
    GoogleSigninButtonModule,],
  imports: [MatDialogModule,
    SocialLoginModule,
    GoogleSigninButtonModule,],
  providers: [
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { hasBackdrop: true, disableClose: true } }, {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '<Google OAuth 2.0 Client ID>'
            ),
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
export class SharedModule { }