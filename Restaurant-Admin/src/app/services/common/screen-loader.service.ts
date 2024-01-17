import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ScreenLoaderService {
  isLoading = new BehaviorSubject<boolean>(false);
  constructor() {}
}
