import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service'; // Importiere AuthService damit "Administration" nur für Admins sichtbar ist

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit, OnDestroy {

  remainingTime!: number;
  private intervalId: any;

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
    //this.startCountdown();
  }

  // startet einen counter um anzuzeigen, wann die token expiration time abgelaufen ist
  startCountdown(): void {
    this.intervalId = setInterval(() => {
      const currentTime = Math.floor(Date.now() / 1000);
      this.remainingTime = this.authService.getExpirationTime() - currentTime;

      // Wenn der Countdown abgelaufen ist
      if (this.remainingTime <= 0) {
        clearInterval(this.intervalId);
        this.remainingTime = 0; // Sicherstellen, dass der Wert genau 0 wird
      }
    }, 1000);  // Jede Sekunde den Countdown aktualisieren
  }

  formatTime(seconds: number): string {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}m ${remainingSeconds}s`;
  }

  showSubMobileMenu(){
    var x = document.getElementById("mobile-menu");
    if(x != null){
      if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
      } else { 
        x.className = x.className.replace(" w3-show", "");
      }
    }
  }

  logout(){
    this.authService.logout();
  }

  ngOnDestroy(): void {
    // Aufräumen, wenn die Komponente zerstört wird
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}
