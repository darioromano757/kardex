import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadMenuComponent } from './load-menu.component';

describe('LoadMenuComponent', () => {
  let component: LoadMenuComponent;
  let fixture: ComponentFixture<LoadMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
