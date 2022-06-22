import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AppUtil } from '../app-util';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  showPass = false;
  showCPass = false;
  inProgress = false;
  passError = 'Required';
  constructor(
    private title: Title,
    private formBuilder: FormBuilder,
    private signUpService: LoginService,
    private snackBar: MatSnackBar,
    private router: Router,
    private appUtil: AppUtil
  ) { }

  ngOnInit(): void {
    this.title.setTitle('Sign Up | Terminus');
  }

  signUpForm = this.formBuilder.group({
    'name': ['', Validators.required],
    'email': ['', [Validators.required, Validators.email]],
    'password': ['', Validators.required],
    'cPassword': ['', Validators.required]
  });

  signUp() {
    let user = {
      'name': this.signUpForm.get('name')?.value,
      'email': this.signUpForm.get('email')?.value,
      'password': this.signUpForm.get('password')?.value
    };

    if (user.password !== this.signUpForm.get('cPassword')?.value) {
      this.signUpForm.get('password')?.setErrors(Validators.required);
      this.signUpForm.get('cPassword')?.setErrors(Validators.required);
      this.passError = 'Password Not Matched';
      return;
    }
    this.inProgress = true;
    this.signUpForm.disable();

    this.signUpService.signUp(user).subscribe({
      next: response => {
        if (response.status === 'success') {
          this.appUtil.setUser(response.success);
          this.router.navigate(['/home']);
        } else {
          this.snackBar.open(response.failure.error.message, 'Close');
        }
        this.inProgress = false;
        this.signUpForm.enable();
      },
      error: error => {
        console.log(error);
        this.inProgress = false;
        this.signUpForm.enable();
        this.snackBar.open('Internal Server Error', 'Close');
      }
    });
  }
}
