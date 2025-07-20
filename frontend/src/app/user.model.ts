export interface User {
    // interface fuer ein user Objekt, s. User Entity im Backend
    id: number;
    email: string;
    password: string;
    registrationDate: Date;
    userRole: string;

}
