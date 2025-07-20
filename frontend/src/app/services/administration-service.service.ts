import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { AdministrationItem } from '../administration-item';

const BASE_URL = "http://localhost:8080/budgetbook-1.0/api";

@Injectable({
  providedIn: 'root'
})
export class AdministrationServiceService {

  // CLi: ng g service services/administration-service

  constructor(private http: HttpClient) { }

  getUsers(token: string): Observable<AdministrationItem[]>{

    
    // token an header setzen
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);

    return this.http.post<AdministrationItem[]>(`${BASE_URL}/administration/getuserlist`, {}, { headers, withCredentials: true })
    .pipe(
      map((items) => {
        // Convert `registrationDate` to Date object for each item
        return items.map(item => ({
          ...item,
          registrationDate: new Date(item.registrationDate)  // Convert to Date object
        }));
      })
    );
  }

  deleteUser(id: number, token: string){
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.delete(`${BASE_URL}/administration/${id}`,  { headers, withCredentials: true });
  }

}
