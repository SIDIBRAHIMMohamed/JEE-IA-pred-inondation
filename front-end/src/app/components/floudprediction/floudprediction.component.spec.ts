import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FloudpredictionComponent } from './floudprediction.component';

describe('FloudpredictionComponent', () => {
  let component: FloudpredictionComponent;
  let fixture: ComponentFixture<FloudpredictionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FloudpredictionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FloudpredictionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
