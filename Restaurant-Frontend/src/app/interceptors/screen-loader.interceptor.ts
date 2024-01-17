import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { ScreenLoaderService } from '../services/common/screen-loader.service';
import { finalize } from 'rxjs/operators';

@Injectable()
export class ScreenLoaderInterceptor implements HttpInterceptor {
  activeRequests: number = 0;

  constructor(private loader: ScreenLoaderService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    if (this.activeRequests === 0) this.loader.isLoading.next(true);
    this.activeRequests++;
    return next.handle(request).pipe(
      finalize(() => {
        this.activeRequests--;
        if (this.activeRequests === 0) this.loader.isLoading.next(false);
      })
    );
  }
}
