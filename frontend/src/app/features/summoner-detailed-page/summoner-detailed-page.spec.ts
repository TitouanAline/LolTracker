import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SummonerDetailedPageComponent } from './summoner-detailed-page';

describe('FriendDetail', () => {
  let component: SummonerDetailedPageComponent;
  let fixture: ComponentFixture<SummonerDetailedPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SummonerDetailedPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SummonerDetailedPageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
