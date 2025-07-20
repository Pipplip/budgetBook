import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BudgetListItem } from 'src/app/budget-list-item.model';
import { BudgetListItemService } from 'src/app/services/budget-list-item.service';
import { StorageService } from 'src/app/services/storage.service';

// reactiveFormsModule muss in app.module.ts importiert werden


@Component({
  selector: 'app-add-item-form',
  templateUrl: './add-item-form.component.html',
  styleUrls: ['./add-item-form.component.scss']
})
export class AddItemFormComponent implements OnInit {

  // Wenn erfolgreich ein Item submitted wird, soll die BudgetList-Component
  // benachrichtigt werden, damit die Component refreshed wird und die neuen
  // Items anzeigt
  @Output() formSubmitted = new EventEmitter<void>();  // Event emitter to notify the parent
  itemForm: FormGroup;

  constructor(private fb: FormBuilder, private budgetListService: BudgetListItemService, private storageService:StorageService) { 
    // Initialize the form group
    this.itemForm = this.fb.group({
      description: ['', Validators.required],
      deposit: [0.0],
      payout: [0.0],
      addedDate: ['', Validators.required],
      user:[6] // Only for test: Assuming a default user ID, adjust as needed
    });
  }

  ngOnInit(): void {
  }

  // Click auf submit vom template
  handleSubmit(){
    if(this.itemForm.valid){
      const formValue = this.itemForm.value;

      // Format the date to 'YYYY-MM-DD' (without time portion)
      const formattedDate = this.formatDate(formValue.addedDate);

      // Convert deposit to a float (number)
      const deposit = parseFloat(formValue.deposit);
      const payout = parseFloat(formValue.payout);

      const newItem: BudgetListItem = {
        ...formValue,
        addedDate: formattedDate,
        deposit: deposit,
        payout: payout,
        user: { id: formValue.user },       // Dynamically use user id (can be selected or inputted)
      };

      const myToken: string | null | void = this.storageService.getToken();

      if(myToken){
        this.budgetListService.addBudgetListItem(newItem, myToken).subscribe({
          next: (response) => {
           console.log('Item added successfully!', response);
 
           this.itemForm.reset({
             description: '',
             deposit: 0.0,
             payout: 0.0,
             addedDate: '',
             user: 0  // Resetting to the default user id for the test user
           });
 
           this.formSubmitted.emit(); // Emit the event to the parent (BudgetListComponent with the budgetListItems$ variable)
         },
         error: (error) => {
           console.log('Error adding item: ', error);
         } 
       });
      }else{
        console.log("Check token");
      }

    }
  }

  // Helper function to format the date to 'YYYY-MM-DD'
  private formatDate(date: string): string {
    const newDate = new Date(date);
    return newDate.toISOString().split('T')[0]; // Extracts the date part only (YYYY-MM-DD)
  }

}
