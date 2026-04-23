import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendsPageComponent } from './friends-page';

describe('Friends', () => {
  let component: FriendsPageComponent;
  let fixture: ComponentFixture<FriendsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendsPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FriendsPageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
