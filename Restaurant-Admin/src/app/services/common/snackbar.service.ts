import { ToastrService } from 'ngx-toastr';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  constructor(private toastr: ToastrService) {}

  success(message: string, title: string): void {
    this.toastr.success(message, title);
  }

  info(message: string, title: string): void {
    this.toastr.info(message, title);
  }

  warning(message: string, title: string): void {
    this.toastr.warning(message, title);
  }

  error(message: string, title: string): void {
    this.toastr.error(message, title);
  }
}
