import { TestBed } from '@angular/core/testing';

import { BudgetListItemService } from './budget-list-item.service';

describe('BudgetListItemService', () => {
  let service: BudgetListItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BudgetListItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
