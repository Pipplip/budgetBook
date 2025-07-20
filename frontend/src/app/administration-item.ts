export interface AdministrationItem {

    // entspricht AdministrationItemDTO auf dem Server
    // CLI: ng g interface administration-item

    registrationDate: Date;
    id: number;
    email: string;
    userRole: string;

}
