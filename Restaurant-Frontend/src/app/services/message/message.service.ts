import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { urls } from '../apiUrls';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  constructor(private http: HttpClient) {}

  forgotPassword(messageObj: any) {
    const url = urls.messageUrls.forget;
    console.log(url);
    return this.http.post(url, messageObj);
  }

  validateOTP(email: string, otp: string) {
    const url = urls.messageUrls.validate
      .replace('{email}', email)
      .replace('{otp}', otp);
    return this.http.get(url);
  }
}
