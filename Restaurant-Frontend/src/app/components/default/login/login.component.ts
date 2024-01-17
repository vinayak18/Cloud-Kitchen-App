import {
  animate,
  state,
  style,
  transition,
  trigger,
  query,
  group,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  FacebookLoginProvider,
  SocialAuthService,
  SocialUser,
} from '@abacritt/angularx-social-login';
import { Router } from '@angular/router';
import { EncryptDecryptService } from 'src/app/services/common/encrypt-decrypt.service';
import { UserService } from 'src/app/services/user-coupon-order/user.service';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { loginCredentials } from 'src/app/models/loginCredentials';
import { userDetails } from 'src/app/models/userDetails';
import { product } from 'src/app/models/product';
import { secretKey } from 'src/app/models/secretKey';
import { CartItemsInfo } from 'src/app/models/cartItemsInfo';
import { ProductService } from 'src/app/services/product-review/product.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('myInsertRemoveTrigger', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('400ms ease', style({ opacity: 1 })),
      ]),
      transition(':leave', [animate('400ms ease', style({ opacity: 0 }))]),
    ]),
  ],
})
export class LoginComponent implements OnInit {
  switch: boolean = true;
  isLoaded: boolean = false;
  loginForm: FormGroup;
  registerForm: FormGroup;
  socialUser: SocialUser;
  userLogged: SocialUser;
  constructor(
    private authService: AuthService,
    private userService: UserService,
    private productService: ProductService,
    private socialAuthService: SocialAuthService,
    private encrypt_decrypt: EncryptDecryptService,
    private router: Router,
    private loader: ScreenLoaderService
  ) { }

  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
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
    this.registerForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
        // Validators.pattern('^w+([.-]?w+)@w+([.-]?w+)(.w{2,3})+$'),
      ]),
      phoneNo: new FormControl('', [Validators.required]),
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

  toggle() {
    this.switch = !this.switch;
  }

  validateUser() {
    const loginObj = new loginCredentials(
      this.loginForm.get('email').value,
      this.loginForm.get('password').value
    );
    this.authService.authenticate(loginObj).subscribe(
      (data) => {
        this.authService.setAuthToken(data.headers.get('Authorization'));
        this.getUser(data.body);
        console.log(data);
      },
      (err) => {
        console.log(err.status);
      }
    );
  }

  checkCartItems(user: userDetails) {
    let cartItemsInfo: CartItemsInfo[] = [];
    let data = sessionStorage.getItem(
      this.encrypt_decrypt.encryption('Cart', secretKey)
    );
    if (data !== null && data !== undefined) {
      cartItemsInfo = JSON.parse(this.encrypt_decrypt.decryption(data, secretKey));

      this.userService
        .addToCartProducts(user.userId, cartItemsInfo)
        .subscribe((user) => {
          this.userService.setUserDetails(user.body);
        });

    } else {
      sessionStorage.setItem(
        this.encrypt_decrypt.encryption('Cart', secretKey),
        this.encrypt_decrypt.encryption(JSON.stringify(user.cart), secretKey)
      );
    }
  }

  registerUser() {
    const userObj = new userDetails(
      null,
      this.registerForm.get('name').value,
      this.registerForm.get('email').value,
      this.registerForm.get('phoneNo').value,
      this.registerForm.get('password').value,
      '../../../assets/img/user.jpeg',
      null,
      [],
      [],
      ['ROLE_USER']
    );
    console.log(userObj);
    this.authService.registerUser(userObj).subscribe((data) => {
      this.toggle();
    });
  }

  getUser(data: any) {
    this.userService.getUserViaEmail(data.email).subscribe((user) => {
      console.log(user);
      this.userService.setUserDetails(user.body);
      this.authService.isLoggedIn.next(true);
      this.checkCartItems(user.body);
      this.router.navigateByUrl('/home');
    });
  }

  signInWithFacebook(): void {
    const fbLoginOptions = {
      scope: 'public_profile',
      locale: 'en_US',
      return_scopes: true,
      enable_profile_selector: true,
      version: 'v13.0',
    };
    this.socialAuthService.signIn(
      FacebookLoginProvider.PROVIDER_ID,
      fbLoginOptions
    );
  }
}
