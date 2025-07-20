import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';  // Importiere den AuthService

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    // Wenn der Benutzer authentifiziert ist, erlaube den Zugriff auf die Route
    if (this.authService.isLoggedIn()) {
      return true;
    } else {
      // Wenn der Benutzer nicht authentifiziert ist, leite ihn zur Login-Seite weiter
      this.router.navigate(['/login']);
      return false;
    }
  }
  
}
