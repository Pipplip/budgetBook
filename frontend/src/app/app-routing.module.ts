import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { BudgetlistComponent } from './budgetlist/budgetlist.component';
import { RegisterComponent } from './register/register.component';
import { AdministrationComponent } from './administration/administration.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './services/auth.guard';
import { RoleGuard } from './role.guard';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'budgetlist', component: BudgetlistComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent },
  // Geschützte Route: Nur authentifizierte Benutzer können auf '/administration' zugreifen
  { path: 'administration', component: AdministrationComponent ,canActivate: [RoleGuard, AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
