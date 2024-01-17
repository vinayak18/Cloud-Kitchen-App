import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { loginCredentials } from 'src/app/models/loginCredentials';
import { EncryptDecryptService } from '../common/encrypt-decrypt.service';
import { catchError } from 'rxjs/operators';
import { userDetails } from 'src/app/models/userDetails';
import { urls } from '../apiUrls';
import { secretKey } from 'src/app/models/secretKey';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn = new BehaviorSubject<boolean>(false);
  constructor(
    private http: HttpClient,
    private encrypt_decrypt: EncryptDecryptService
  ) { }

  registerUser(userObj: userDetails): Observable<any> {
    const url = urls.userUrls.register;
    return this.http.post(url, userObj);
  }

  authenticate(loginObj: loginCredentials): Observable<any> {
    const url = urls.userUrls.login;
    return this.http.get(url, {
      headers: {
        authorization: this.createBasicAuthToken(
          loginObj.username,
          loginObj.password
        ),
        'X-Requested-With': 'XMLHttpRequest',
      },
      observe: 'response',
      withCredentials: true,
    });
  }

  createBasicAuthToken(username: String, password: String) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }

  googleAuthentication(token: any): Observable<any> {
    const url = urls.userUrls.socialGoogleLogin;
    return this.http.post(url, token);
  }

  fbAuthentication(id: string, token: any): Observable<any> {
    const url = urls.userUrls.socialFbLogin.replace('{id}', '' + id);
    return this.http.post(url, token);
  }

  getAuthToken(): string {
    return this.encrypt_decrypt.decryption(
      sessionStorage.getItem(
        this.encrypt_decrypt.encryption('Authorization', secretKey)
      ),
      secretKey
    );
  }

  setAuthToken(token: string): void {
    sessionStorage.setItem(
      this.encrypt_decrypt.encryption('Authorization', secretKey),
      this.encrypt_decrypt.encryption(token, secretKey)
    );
  }
}
