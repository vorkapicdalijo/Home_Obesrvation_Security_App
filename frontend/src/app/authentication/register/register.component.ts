import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registrationForm!: FormGroup;

  registrationSuccessful: boolean = false;
  registrationFailed: boolean = false;
  registrationSubmitted: boolean = false;
  errorMessage: string = '';

  constructor(private fb: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router) {}

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      username: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.compose(
        [Validators.minLength(6), Validators.required])),
      confirmPassword: new FormControl('', Validators.required)
    });
  }

  public onSubmit(): void {
    let username = this.registrationForm.get('username')?.value;
    let email = this.registrationForm.get('email')?.value;
    let password = this.registrationForm.get('password')?.value;
    let confirmPassword = this.registrationForm.get('confirmPassword')?.value;

    this.registrationSubmitted = true;

    this.authenticationService.registerUser(username, email, password, confirmPassword)
      .subscribe({
        next: response => {
          console.log(response);
          this.registrationSuccessful = true;
          this.router.navigate(['/home']);
        },
        error: error => {
          this.registrationFailed = true;
          this.errorMessage = error.error.message;

        }
      })
  }

  public loginInstead() {
    this.router.navigate(['login']);
  }
}
