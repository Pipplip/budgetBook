import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService, RegisterRequest } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  errorMessage = '';
  isLoading= false;
  

  constructor(private authService: AuthService, private fb: FormBuilder) { 
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirm_password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['USER']
    }, {
      validators: this.passwordsMatchValidator  // Custom validator to check password and confirm password match
    });
  }

  // Custom validator to check that passwords match
  passwordsMatchValidator(formGroup: FormGroup): null | { passwordsDoNotMatch: boolean } {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirm_password')?.value;

    if (password && confirmPassword && password !== confirmPassword) {
      return { passwordsDoNotMatch: true };
    }
    return null;
  }

  ngOnInit(): void {
  }

  onSubmit(){
    if(this.registerForm.valid){
      this.isLoading = true;  // Show the spinner
      this.errorMessage = '';

      const registerRequest: RegisterRequest = this.registerForm.value;
      this.authService.register(registerRequest).subscribe(
        (response) => {
          console.log('Registration successful', response);
          // Handle success (e.g., redirect, show success message, etc.)
          this.isLoading = false; // Hide the spinner when the request is complete
        },
        (error) => {
          console.error('Registration failed', error);
          this.errorMessage = error; //'Registration failed. Please try again.';
          this.isLoading = false; // Hide the spinner when the request is complete
        }
      );
    }else{
      this.errorMessage = 'Bitte alle Felder ausfuellen';
    }
  }

}
