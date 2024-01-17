import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../services/user-coupon-order/user.service';
import { AuthService } from '../services/auth/auth.service';
import { SnackbarService } from '../services/common/snackbar.service';
import { EncryptDecryptService } from '../services/common/encrypt-decrypt.service';
import { secretKey } from '../models/secretKey';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private userService: UserService,
    private router: Router,
    private authService: AuthService,
    private snackbarService: SnackbarService,
    private encrypt_decrypt: EncryptDecryptService
  ) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const currUser = this.userService.getCurrentUserDetails();
    if (null != currUser) {
      this.authService.isLoggedIn.next(true);
      const orderSummary = sessionStorage.getItem(
        this.encrypt_decrypt.encryption('Order Summary', secretKey)
      );
      console.log('summary');
      console.log(route);
      if (
        route.routeConfig.path === 'order/confirmation' &&
        (orderSummary === undefined || orderSummary === null)
      ) {
        this.router.navigateByUrl('/home');
        return false;
      }
      return true;
    } else {
      this.authService.isLoggedIn.next(false);
      this.snackbarService.warning('Please login to continue.', '');
      this.router.navigateByUrl('/login');
      return false;
    }
  }
}
