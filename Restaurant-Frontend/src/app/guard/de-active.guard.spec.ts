import { TestBed } from '@angular/core/testing';

import { DeActiveGuard } from './de-active.guard';

describe('DeActiveGuard', () => {
  let guard: DeActiveGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(DeActiveGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
