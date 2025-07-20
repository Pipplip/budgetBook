import { Injectable } from '@angular/core';
import { BudgetListItem } from '../budget-list-item.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';

const BASE_URL = "http://localhost:8080/budgetbook-1.0/api";

@Injectable({
  providedIn: 'root' // Ensures that the service is provided globally
})
export class BudgetListItemService {

  // hardcoded fuer implementierung, wird aus der DB ueber http geholt
  /*budgetListItems: BudgetListItem[] = [
    {
      id: 1,
      deposit: 500, // Einzahlung
      payout: 0, // Auszahlung
      description: "Geschenk",
      addedDate: new Date("2025-01-02"),
      user: 1
   },
  ]*/

  constructor(private http: HttpClient) { }

  // getBudgetListItems: HTTP request
  getBudgetListItems(): Observable<BudgetListItem[]>{
    return this.http.get<BudgetListItem[]>(`${BASE_URL}/budgetlistItem`);
  }

  getBudgetListItemsByUserId(userIdentifier: number, token: string): Observable<BudgetListItem[]>{

    const body = { userIdentifier }; // erstelle den body mit der userId

    //const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',  // Setze den Content-Type auf JSON
      'Authorization': `Bearer ` + token  // Wenn Authentifizierung erforderlich ist
    });

    //return this.http.post<BudgetListItem[]>(`${BASE_URL}/budgetlistItem/getitemsbyuser`, body, { headers });
  
    return this.http.post<BudgetListItem[]>(`${BASE_URL}/budgetlistItem/getitemsbyuser`, body, { headers, withCredentials: true })
    .pipe(
      map((items) => {
        // Convert `addedDate` to Date object for each item
        return items.map(item => ({
          ...item,
          addedDate: new Date(item.addedDate)  // Convert to Date object
        }));
      })
    );
  
  }

  // add BudgetListItem
  addBudgetListItem(budgetListItem: BudgetListItem, token: string){
    // Hardcoded fuer Implementierung
    //this.budgetListItems.push(budgetListItem);
    //return this.budgetListItems;

    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    //const headers = new HttpHeaders({
    //  'Content-Type': 'application/json',  // Setze den Content-Type auf JSON
    //  'Authorization': `Bearer ` + token  // Wenn Authentifizierung erforderlich ist
    //});

    return this.http.post(`${BASE_URL}/budgetlistItem`, { ...budgetListItem }, { headers, withCredentials: true });
  }

  // update BudgetListItem
  updateBudgetListItem(newBudgetListItem: BudgetListItem){
    //const listIndex = this.budgetListItems.findIndex( budgetListItem => budgetListItem.id === newBudgetListItem.id);
    //this.budgetListItems[listIndex] = newBudgetListItem;
    //return this.budgetListItems;
    this.http.put(`${BASE_URL}/budgetlistItem/${newBudgetListItem.id}`, { ...newBudgetListItem});
  }

  // delete BudgetListItem
  deleteBudgetListItem(id: number, token: string){
    //const listIndex = this.budgetListItems.findIndex( budgetListItem => budgetListItem.id === id);
    //this.budgetListItems.splice(listIndex, 1);
    //return this.budgetListItems;

    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);

    //return this.http.delete(`${BASE_URL}/budgetlistItem/${id}`);
    return this.http.delete(`${BASE_URL}/budgetlistItem/${id}`,  { headers, withCredentials: true });
  }

}
