import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';
import { catchError, tap, throwError } from 'rxjs';

type TokenResponse = {
  token: string;
};

export type RegisterRequest = {
  email: string,
  password: string,
  role: "USER" | "ADMIN";
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = "http://localhost:8080/budgetbook-1.0/api/auth";

  constructor(private http: HttpClient, private storageService: StorageService, private router: Router) { }

  login(email: string, password: string) {
    //localStorage.setItem(this.authTokenKey, token);
    return this.http
      .post<TokenResponse>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap((response) => {
        this.storageService.setToken(response.token);
        this.router.navigate(['/']);
      }),
      catchError(this.handleError)
    );
  }

  register({email, password, role}: RegisterRequest){
    return this.http
      .post<TokenResponse>(`${this.apiUrl}/register`, { email, password, role })
      .pipe(
        tap((response) => {
        this.storageService.setToken(response.token);
        this.router.navigate(['/']);
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    
    if (error.error instanceof ErrorEvent) {
      // Client-seitiger Fehler
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-seitiger Fehler
      switch (error.status) {
        case 400:
          errorMessage = 'Bad request. Please check your input.';
          break;
        case 401:
          errorMessage = `${error.error.message}`;
          break;
        case 403:
          errorMessage = 'Forbidden. You do not have permission to access this resource.';
          break;
        case 404:
          errorMessage = 'Resource not found.';
          break;
        case 500:
          errorMessage = 'Internal server error. Please try again later.';
          break;
        case 423:
          // Fehler bei einer gesperrten Benutzeranmeldung (z.B. Account gesperrt)
          //errorMessage = `${error.error.error}`;
          errorMessage = 'Your account is locked due to multiple failed login attempts. Please try again later.';
          break;
        default:
          errorMessage = `Unexpected error occurred: ${error.message}`;
          break;
      }
    }

    // Fehler an die Konsole loggen (optional)
    console.error('Error occurred:', error);

    // Rückgabe des Fehler-Streams für den Fehlerhandler in den Komponenten
    return throwError(errorMessage);
  }

  // Rolle aus dem Token extrahieren
  isAdmin(): Boolean | null {
    const token = this.storageService.getToken();
    if (token) {
      const payload = this.decodeToken(token);
      //console.log(payload?.role);
      return payload?.role === 'ADMIN' || null;  // Die Rolle aus dem Token zurückgeben
    }
    return null;
  }

  getUserId(){
    const token = this.storageService.getToken();
    if (token) {
      const payload = this.decodeToken(token);
      //console.log(payload?.userId);
      return payload?.userId;  // Der userId claim aus dem token lesen
    }
    return '';
  }

  getEmailAdressOfLoggedInUser(){
    const token = this.storageService.getToken();
    if (token) {
      const payload = this.decodeToken(token);
      //console.log(payload?.sub);
      return payload?.sub;  // Der sub claim aus dem token lesen
    }
    return '';
  }

  // Token dekodieren (nicht zur Validierung, sondern nur zur Extraktion von Informationen)
  private decodeToken(token: string): any {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload));  // Base64-decoding des Payloads
  }

  logout(): void {
    // nur aus dem localStorage zu löschen ist nicht sicher
    // TODO auf server logout einbauen
    this.storageService.clearToken();
    this.router.navigate(['/login']);
  }

  isLoggedIn(){
    //console.log(Boolean(this.storageService.getToken()));
    return Boolean(this.storageService.getToken());
  }

  getExpirationTime(){
    const token = this.storageService.getToken();
    if (token) {
      const payload = this.decodeToken(token);
      return payload?.exp;  // Der sub claim aus dem token lesen
    }
    return '';
  }

}
