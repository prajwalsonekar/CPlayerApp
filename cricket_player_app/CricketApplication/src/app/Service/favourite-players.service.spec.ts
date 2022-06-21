import { TestBed } from '@angular/core/testing';

import { FavouritePlayersService } from './favourite-players.service';

describe('FavouritePlayersService', () => {
  let service: FavouritePlayersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavouritePlayersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
