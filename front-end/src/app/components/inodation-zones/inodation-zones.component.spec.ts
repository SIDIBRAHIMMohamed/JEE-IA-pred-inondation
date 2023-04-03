import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InodationZonesComponent } from './inodation-zones.component';

describe('InodationZonesComponent', () => {
  let component: InodationZonesComponent;
  let fixture: ComponentFixture<InodationZonesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InodationZonesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InodationZonesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
