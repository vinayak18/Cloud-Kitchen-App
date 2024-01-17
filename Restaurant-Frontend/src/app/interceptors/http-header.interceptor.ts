import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';

@Injectable()
export class HttpHeaderInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let modified = request;
    if (!request.url.toString().includes('/verify/')) {
      modified = request
    }
    else{
    const jwt = this.authService.getAuthToken();
      modified = request.clone({
        setHeaders: { Authorization: 'Bearer ' + jwt },
        withCredentials: true,
      }); 
    }
    return next.handle(modified);
  }
}
