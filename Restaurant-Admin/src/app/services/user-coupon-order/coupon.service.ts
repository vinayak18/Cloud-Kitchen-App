import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { EncryptDecryptService } from '../common/encrypt-decrypt.service';
import { HttpClient } from '@angular/common/http';
import { urls } from '../apiUrls';
import { coupon } from 'src/app/models/coupon';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CouponService {
  constructor(
    private http: HttpClient,
    private encrypt_decrypt: EncryptDecryptService,
    private auth: AuthService
  ) {}

  getCouponViaCode(couponData: any): Observable<coupon> {
    const url = urls.couponUrls.byCode;
    return this.http.post<coupon>(url, couponData);
  }
}
