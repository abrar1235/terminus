import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AppUtil } from '../app-util';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  showPass = false;
  inProgress = false;
  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private snackBar: MatSnackBar,
    private router: Router,
    private appUtil: AppUtil
  ) { }

  ngOnInit(): void {
  }

  loginForm = this.formBuilder.group({
    'email': ['', Validators.required],
    'password': ['', Validators.required]
  });

  login() {
    let credentials = {
      'email': this.loginForm.get('email')?.value,
      'password': this.loginForm.get('password')?.value
    };

    this.inProgress = true;
    this.loginForm.disable();
    this.loginService.login(credentials).subscribe({
      next: response => {
        if (response.status === 'success') {
          this.appUtil.setUser(response.success);
          this.router.navigate(['/home']);
        } else {
          this.snackBar.open(response.failure.error.message, 'Close');
        }
        this.inProgress = false;
        this.loginForm.enable();
      },
      error: error => {
        console.log('error', error);
        this.snackBar.open('Internal Server Error', 'Close');
        this.inProgress = false;
        this.loginForm.enable();
      }
    });

  }
}
