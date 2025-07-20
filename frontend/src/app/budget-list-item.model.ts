export interface BudgetListItem {

    id: number;
    
    deposit: number; // Einzahlung
    payout: number; // Auszahlung
    
    description: string;
    addedDate: Date;
     
    userId: number;
}
