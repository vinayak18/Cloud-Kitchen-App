import { IndividualConfig, ToastrService } from 'ngx-toastr';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  constructor(private toastr: ToastrService) { }

  success(message: string, title: string, override?: Partial<IndividualConfig<any>>): void {
    this.toastr.success(message, title, override);
  }

  info(message: string, title: string, override?: Partial<IndividualConfig<any>>): void {
    this.toastr.info(message, title, override);
  }

  warning(message: string, title: string, override?: Partial<IndividualConfig<any>>): void {
    this.toastr.warning(message, title, override);
  }

  error(message: string, title: string, override?: Partial<IndividualConfig<any>>): void {
    this.toastr.error(message, title, override);
  }
}
