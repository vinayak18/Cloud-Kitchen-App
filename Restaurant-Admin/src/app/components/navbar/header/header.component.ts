import { SocialAuthService } from '@abacritt/angularx-social-login';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { userDetails } from 'src/app/models/userDetails';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user-coupon-order/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  firstName: string = '';
  currUser: userDetails = {} as userDetails;
  constructor(
    private authService: AuthService,
    private router: Router,
    private socialAuthService: SocialAuthService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn.subscribe((value) => {
      this.isLoggedIn = value;
      if (this.isLoggedIn) {
        this.currUser = this.userService.getCurrentUserDetails();
        this.firstName = this.currUser.name.split(' ')[0];
      }
    });
  }

  async logout(): Promise<void> {
    // For app-user sign out logic we have used try-catch to prevent getting error from social-login-user sign out
    try {
      await this.socialAuthService.signOut().then(() => {
        console.log('User signed out');
      }).catch((error) => {
        console.log('Error occured in sign out');
      });
    } catch (error) {
      console.log(error);
    }
    sessionStorage.clear();
    this.authService.isLoggedIn.next(false);
    this.router.navigateByUrl('/login');
  }
}
