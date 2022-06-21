import { TestBed } from '@angular/core/testing';

import { PlayerdetailServiceService } from './playerdetail-service.service';

describe('PlayerdetailServiceService', () => {
  let service: PlayerdetailServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlayerdetailServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
