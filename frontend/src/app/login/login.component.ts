import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  errorMessage = '';
  isLoading = false; // Indicator, wenn auf Login geklickt wird

  constructor(private authService: AuthService, private fb: FormBuilder) { 
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
  }

  onSubmit(){
    if(this.loginForm.valid){

      this.isLoading = true;  // Show the spinner
      this.errorMessage = '';

      this.authService.login(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe(
        (response) => {
          console.log('Login succesful', response);
          // Handle success (e.g., redirect, show success message, etc.)
          this.isLoading = false; // Hide the spinner when the request is complete
        },
        (error) => {
          console.error('Login failed', error);
          this.errorMessage = error; // 'Login Failed. Try again.'
          this.isLoading = false; // Hide the spinner when the request is complete
        }
      );
    }else{
      this.errorMessage = 'Bitte alle Felder ausfuellen';
    }
  }

}
