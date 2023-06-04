import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';
import { StorageService } from '../services/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  loginSuccessful: boolean = false;
  loginFailed: boolean = false;
  loginSubmitted: boolean = false;
  errorMessage: string = '';

  isLoading: boolean = false;

  constructor(private fb: FormBuilder,
              private authenticationService: AuthenticationService,
              private storageService: StorageService,
              private router: Router) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  public onSubmit(): void {
    this.isLoading = true;
    let username = this.loginForm.get('username')?.value;
    let password = this.loginForm.get('password')?.value;

    this.loginSubmitted = true;

    this.authenticationService.loginUser(username, password)
      .subscribe({
        next: response => {
          this.storageService.saveUser(response);
          this.isLoading = false;
          this.loginSuccessful = true;
          this.router.navigate(['/home'])
        },
        error: error => {
          this.isLoading = false;
          this.loginFailed = true;
          this.errorMessage = error.error.message;
        }
      })
  }

  public registerInstead() {
    this.router.navigate(['register']);
  }

  private reloadPage(): void {
    window.location.reload();
  }
}
