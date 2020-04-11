import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PositionSelectionComponent } from './positionselection.component';

describe('PositionSelectionComponent', () => {
  let component: PositionSelectionComponent;
  let fixture: ComponentFixture<PositionSelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PositionSelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PositionSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
