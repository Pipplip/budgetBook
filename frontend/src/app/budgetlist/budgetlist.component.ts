import { Component, OnInit, SimpleChanges } from '@angular/core';
import { BudgetListItem } from '../budget-list-item.model';
import { BudgetListItemService } from '../services/budget-list-item.service';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-budgetlist',
  templateUrl: './budgetlist.component.html',
  styleUrls: ['./budgetlist.component.scss']
})

export class BudgetlistComponent implements OnInit{


  //budgetListItems: BudgetListItem[] = []; // needed for hardcoded implementation
  budgetListItems$!: Observable<BudgetListItem[]>;

  errorMessage = '';

  // Injecte den BudgetListItem Service als Konstruktor-Paramater
  // Ein Service ist fuer alle Componenten verfuegbar
  constructor(private budgetListService: BudgetListItemService, private authService: AuthService, private storageService: StorageService) { }

  ngOnInit(): void {
    //this.budgetListItems = this.budgetListService.getBudgetListItems();
    //this.updateBudgetListItems();

    //console.log('init data by id');

    this.errorMessage = '';
    const myToken: string | null | void = this.storageService.getToken();
    
    if(myToken){
      this.updateBudgetListItemsByUserId(this.authService.getUserId(), myToken)
    }else{
      console.error("Check token");
      this.errorMessage = 'An error occured!';
    }
  }

  ngOnChanges(changes: SimpleChanges){
    
  }

  // http request vom Service um items einer userId zurueckzugeben
  updateBudgetListItemsByUserId(userId: number, token: string){
    this.budgetListItems$ = this.budgetListService.getBudgetListItemsByUserId(userId, token);
  }

  // http request vom Service um alle items zurueckzugeben
  updateBudgetListItems(){
    this.budgetListItems$ = this.budgetListService.getBudgetListItems();
  }

  onDeleteClick(id: number){
    //console.log(id);
    //this.budgetListItems = this.budgetListService.deleteBudgetListItem(id);
    
    const myToken: string | null | void = this.storageService.getToken();
    if(myToken){
      this.budgetListService.deleteBudgetListItem(id, myToken).subscribe(() => {
        this.updateBudgetListItemsByUserId(this.authService.getUserId(), myToken);
      });
    }
    
  }

  // Hier landen wir, wenn im add-item-form ein Item erfolgreich hinzugefügt wurde.
  // Dort wurde this.formSubmitted.emit(); ausgeführt
  // welches wiederum im HTML der Component zu finden ist:
  // (formSubmitted)="refreshList()"
  //
  // submit is done in the form component, refresh the BudgetListComponent
  // to see the new items
  refreshList(){
    this.errorMessage = '';
    const myToken: string | null | void = this.storageService.getToken();
    
    if(myToken){
      this.updateBudgetListItemsByUserId(this.authService.getUserId(), myToken)
    }else{
      console.error("Check token");
      this.errorMessage = 'An error occured!';
    }
  }

}
