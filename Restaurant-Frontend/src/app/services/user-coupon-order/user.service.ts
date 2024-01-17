import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EncryptDecryptService } from '../common/encrypt-decrypt.service';
import { urls } from '../apiUrls';
import { AuthService } from '../auth/auth.service';
import { Observable, throwError } from 'rxjs';
import { secretKey } from 'src/app/models/secretKey';
import { product } from 'src/app/models/product';
import { userDetails } from 'src/app/models/userDetails';
import { blobImage } from 'src/app/models/blobImage';
import { CartItemsInfo } from 'src/app/models/cartItemsInfo';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private http: HttpClient,
    private encrypt_decrypt: EncryptDecryptService,
    private auth: AuthService
  ) {
    const currUser = this.getCurrentUserDetails();
    if (null != currUser) {
      this.auth.isLoggedIn.next(true);
    }
  }

  getUserViaEmail(email: string): Observable<any> {
    const url = urls.userUrls.byEmail.replace('{email}', '' + email);
    return this.http.get(url, {
      observe: 'response',
    });
  }

  getUserViaUsedId(id: string): Observable<any> {
    const url = urls.userUrls.byId.replace('{id}', '' + id);
    return this.http.get(url, {
      observe: 'response',
    });
  }

  getCurrentUserDetails() {
    let currUser = null;
    let data = sessionStorage.getItem(
      this.encrypt_decrypt.encryption('UserDetails', secretKey)
    );
    if (null != data) {
      currUser = JSON.parse(this.encrypt_decrypt.decryption(data, secretKey));
      console.log(currUser);
    }
    return currUser;
  }
  updateUser(user: userDetails): Observable<userDetails> {
    const url = urls.userUrls.updateUser;
    return this.http.put<userDetails>(url, user);
  }
  resetPassword(resetPwdObj: any) {
    const url = urls.userUrls.resetPassword;
    console.log(resetPwdObj);
    return this.http.put(url, resetPwdObj);
  }
  setUserDetails(user: userDetails): void {
    sessionStorage.setItem(
      this.encrypt_decrypt.encryption('UserDetails', secretKey),
      this.encrypt_decrypt.encryption(JSON.stringify(user), secretKey)
    );
    console.log(this.getCurrentUserDetails());
  }

  addToCartProducts(email: string, items: CartItemsInfo[]): Observable<any> {
    const url = urls.userUrls.addToCart.replace('{email}', '' + email);
    let isLoggedIn = false;
    this.auth.isLoggedIn.subscribe((data) => {
      isLoggedIn = data;
    });
    return this.http.put(url, items, {
      observe: 'response',
      params: new HttpParams().set('isLoggedIn', isLoggedIn),
    });
  }

  uploadUserImage(userId: string, imageData: FormData) {
    const url = urls.userUrls.uploadImage.replace('{userId}', '' + userId);
    return this.http.post<userDetails>(url, imageData);
  }

  getUserImage(image: blobImage) {
    const url = urls.userUrls.getImage;
    return this.http.post(url, image);
  }
}
