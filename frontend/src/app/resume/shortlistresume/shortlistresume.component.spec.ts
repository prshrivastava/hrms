import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShortlistresumeComponent } from './shortlistresume.component';

describe('ShortlistresumeComponent', () => {
  let component: ShortlistresumeComponent;
  let fixture: ComponentFixture<ShortlistresumeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShortlistresumeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShortlistresumeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
