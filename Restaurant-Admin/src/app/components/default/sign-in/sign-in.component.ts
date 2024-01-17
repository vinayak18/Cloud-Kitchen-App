import { SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { loginCredentials } from 'src/app/models/loginCredentials';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { UserService } from 'src/app/services/user-coupon-order/user.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  loginForm: FormGroup;
  isLoaded: boolean;
  socialUser: SocialUser;
  userLogged: SocialUser;
  constructor(private authService: AuthService,
    private socialAuthService: SocialAuthService, private userService: UserService, private router: Router, private loader: ScreenLoaderService) { }

  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    })
    this.loginForm = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
        // Validators.pattern('^w+([.-]?w+)@w+([.-]?w+)(.w{2,3})+$'),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(20),
        // Validators.pattern('^[ A-Za-z0-9_@$!./#&+-]*$'),
      ]),
    });

    this.socialAuthService.authState.subscribe(
      (data) => {
        console.log(data);
        this.userLogged = data;
        if (
          null != this.userLogged &&
          undefined != this.userLogged &&
          null != this.userLogged.authToken &&
          undefined != this.userLogged.authToken
        ) {
          console.log(this.userLogged);
          let socialLoginToken = { value: this.userLogged.authToken };
          this.authService
            .fbAuthentication(this.userLogged.id, socialLoginToken)
            .subscribe(
              (data) => {
                this.authService.setAuthToken(data.token);
                this.getUser(data);
              },
              (err) => {
                console.log(err);
              }
            );
        }
        if (
          null != this.userLogged &&
          undefined != this.userLogged &&
          null != this.userLogged.idToken &&
          undefined != this.userLogged.idToken
        ) {
          let socialLoginToken = { value: this.userLogged.idToken };
          this.authService
            .googleAuthentication(socialLoginToken)
            .subscribe((data) => {
              console.log(data);
              this.authService.setAuthToken(data.token);
              this.getUser(data);
            });
        } else if (null != this.userLogged) {
        }
      },
      (err) => {
        console.log(err);
      }
    );
  }

  validateUser() {
    const loginObj = new loginCredentials(
      this.loginForm.get('email').value,
      this.loginForm.get('password').value
    );
    console.log(loginObj);
    this.authService.authenticate(loginObj).subscribe((data) => {
      this.authService.setAuthToken(data.headers.get('Authorization'));
      this.getUser(data.body);
    });

  }

  getUser(data: any) {
    this.userService.getUserViaEmail(data.email).subscribe((user) => {
      console.log(user);
      this.userService.setUserDetails(user.body);
      this.authService.isLoggedIn.next(true);
      this.router.navigateByUrl('/dashboard');
    });
  }

}
