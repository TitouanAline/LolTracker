import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendsDetailComponent } from './friend-detail';

describe('FriendDetail', () => {
  let component: FriendsDetailComponent;
  let fixture: ComponentFixture<FriendsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendsDetailComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FriendsDetailComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
