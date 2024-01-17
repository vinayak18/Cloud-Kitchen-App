import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { SnackbarService } from '../services/common/snackbar.service';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private snackbarSerivce: SnackbarService,
    private router: Router
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    return next
      .handle(request)
      .pipe(catchError((error) => this.handleError(error, request)));
  }

  handleError(error: HttpErrorResponse, request: HttpRequest<unknown>) {
    let errorMessage = '';
    if (error.status === 401) {
      console.log(error.status);
      if (request.url.toString().includes('/verify/')) {
        errorMessage = 'Session Timeout';
      } else {
        errorMessage = 'Invalid Username or Password';
      }
      sessionStorage.clear();
      this.authService.isLoggedIn.next(false);
      this.router.navigateByUrl('/login');
      this.snackbarSerivce.error(errorMessage, '');
      return throwError(error);
    }
    else{
      return throwError(error);
    }
  }
}
