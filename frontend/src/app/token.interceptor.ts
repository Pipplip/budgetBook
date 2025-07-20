import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { StorageService } from './services/storage.service';
import { Router } from '@angular/router';
import { error } from 'console';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  // Ein Interceptor gibt in diesem Fall fuer alle Anfragen immer den Auth Token mit

  constructor(private storageService: StorageService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    
    // Hole das Token aus dem Storage
    const token = this.storageService.getToken();

    // Wenn ein Token existiert, füge es dem Request hinzu
    if(token){
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    
    // Anfrage ausführen und Fehler behandeln
    return next.handle(request).pipe(
      catchError((error) => {
        if(error.status === 403){
          // Wenn der Server "Unauthorized" zurückgibt (401), leite auf die Login-Seite weiter
          this.storageService.clearToken();
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}
