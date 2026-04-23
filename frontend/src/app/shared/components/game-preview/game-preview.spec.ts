import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GamePreviewComponent } from './game-preview';

describe('GamePreview', () => {
  let component: GamePreviewComponent;
  let fixture: ComponentFixture<GamePreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GamePreviewComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GamePreviewComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
