import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/common/snackbar.service';
import { UserService } from 'src/app/services/user-coupon-order/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  imgPath: string = '../../../assets/img/logo1.jpeg';
  resetFormGroup: FormGroup;
  email: string;
  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private snackbar: SnackbarService,
    private userService: UserService
  ) {
    this.email = this.router.getCurrentNavigation().extras.state.email;
  }
  ngOnInit(): void {
    this.resetFormGroup = this.formBuilder.group({
      new_pwd: ['', Validators.required],
      confirm_pwd: ['', Validators.required],
    });
  }
  resetPassword() {
    let new_pwd = this.resetFormGroup.get('new_pwd').value;
    let confirm_pwd = this.resetFormGroup.get('confirm_pwd').value;
    if (new_pwd !== confirm_pwd) {
      return this.snackbar.error(
        'Confirm password is not identical',
        'Try Again!'
      );
    }
    this.userService
      .resetPassword({ email: this.email, new_pwd: new_pwd })
      .subscribe(
        (data) => {
          this.snackbar.success('Password update successfully.', '');
          this.router.navigateByUrl('/login');
        },
        (error) => {
          this.snackbar.error('Something went wrong.', 'Try Again!');
        }
      );
  }
}
