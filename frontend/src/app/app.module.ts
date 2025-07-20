import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NavigationComponent } from './navigation/navigation.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { BudgetlistComponent } from './budgetlist/budgetlist.component';
import { AdministrationComponent } from './administration/administration.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// material.angular.io Icons importieren
import { MatIconModule } from '@angular/material/icon';
import { HomeComponent } from './home/home.component';
import { AddItemFormComponent } from './budgetlist/add-item-form/add-item-form.component';

import { ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http'; // Import the HttpClientModule

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationComponent,
    LoginComponent,
    RegisterComponent,
    BudgetlistComponent,
    AdministrationComponent,
    HomeComponent,
    AddItemFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, // Hier wird das Routing-Modul importiert
    BrowserAnimationsModule,
    MatIconModule,
    ReactiveFormsModule, // Form Group importieren
    HttpClientModule // HttpClient import
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
