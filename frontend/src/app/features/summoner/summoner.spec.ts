import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Summoner } from './summoner';

describe('Summoner', () => {
  let component: Summoner;
  let fixture: ComponentFixture<Summoner>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Summoner],
    }).compileComponents();

    fixture = TestBed.createComponent(Summoner);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
