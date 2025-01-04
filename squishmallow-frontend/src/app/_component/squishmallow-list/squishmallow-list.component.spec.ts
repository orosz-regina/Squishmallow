import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SquishmallowListComponent } from './squishmallow-list.component';

describe('SquishmallowListComponent', () => {
  let component: SquishmallowListComponent;
  let fixture: ComponentFixture<SquishmallowListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SquishmallowListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SquishmallowListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
