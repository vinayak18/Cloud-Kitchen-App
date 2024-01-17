import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/common/snackbar.service';
import { MessageService } from 'src/app/services/message/message.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css'],
})
export class ForgetPasswordComponent implements OnInit {
  imgPath: string = '../../../assets/img/logo1.jpeg';
  checkOtp: boolean = false;
  forgetFormGroup: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private snackbar: SnackbarService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.forgetFormGroup = this.formBuilder.group({
      email: ['', Validators.required],
      phoneNo: [''],
      otp: ['', [Validators.pattern('^d{6}$'), Validators.maxLength(6)]],
    });
  }

  validateForgetForm() {
    let messageObj = {
      email: this.forgetFormGroup.get('email').value,
      phoneNo: this.forgetFormGroup.get('phoneNo').value,
    };
    this.messageService.forgotPassword(messageObj).subscribe(
      (data) => {
        this.snackbar.success('OTP sent successfully.', '');
        this.checkOtp = true;
      },
      (error) => {
        this.snackbar.error("User doesn't exist.", 'Try Again!');
      }
    );
  }

  validateOtp() {
    let email = this.forgetFormGroup.get('email').value;
    let otp = this.forgetFormGroup.get('otp').value;
    this.messageService.validateOTP(email, otp).subscribe(
      (data) => {
        this.snackbar.success('Validation successful.', '');
        // this.router.navigateByUrl('/change-password/' + email);
        this.router.navigateByUrl('/change-password', {
          state: { email: email },
        });
      },
      (error) => {
        this.snackbar.error('Invalid OTP.', 'Try Again!');
      }
    );
  }
}
