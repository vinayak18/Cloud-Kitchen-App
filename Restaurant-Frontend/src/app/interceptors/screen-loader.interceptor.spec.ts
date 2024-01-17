import { TestBed } from '@angular/core/testing';

import { ScreenLoaderInterceptor } from './screen-loader.interceptor';

describe('ScreenLoaderInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ScreenLoaderInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: ScreenLoaderInterceptor = TestBed.inject(ScreenLoaderInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
