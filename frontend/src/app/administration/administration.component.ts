import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AdministrationItem } from '../administration-item';
import { AdministrationServiceService } from '../services/administration-service.service';
import { AuthService } from '../services/auth.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
  styleUrls: ['./administration.component.scss']
})
export class AdministrationComponent implements OnInit {

  administrationItems$!: Observable<AdministrationItem[]>;

  errorMessage = '';

  constructor(private administrationService: AdministrationServiceService, public authService: AuthService, private storageService: StorageService) { }

  ngOnInit(): void {
    this.errorMessage = '';
    const myToken: string | null | void = this.storageService.getToken();
    if(myToken){
      this.getUsers(myToken)
    }else{
      console.error("Check token");
      this.errorMessage = 'An error occured!';
    }
  }

  getUsers(token: string){
    this.administrationItems$ = this.administrationService.getUsers(token);
  }

  onDeleteClick(id:number){
    const myToken: string | null | void = this.storageService.getToken();
    if(myToken){
      this.administrationService.deleteUser(id, myToken).subscribe(() => {
        this.getUsers(myToken);
      });
    }
  }

}
