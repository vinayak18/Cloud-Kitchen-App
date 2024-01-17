import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialDishesComponent } from './special-dishes.component';

describe('SpecialDishesComponent', () => {
  let component: SpecialDishesComponent;
  let fixture: ComponentFixture<SpecialDishesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpecialDishesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecialDishesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
