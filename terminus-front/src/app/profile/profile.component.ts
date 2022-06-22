import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  greeting = '';
  name: any = '';
  email: any = '';
  profession: any = '';
  users: any[] = [];
  searchForm = new FormControl('');
  userId: any;
  updateForm: any;
  inProgress = false;

  constructor(
    private title: Title,
    private router: Router,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder
  ) {

  }

  ngOnInit(): void {
    this.name = localStorage.getItem('name');
    this.email = localStorage.getItem('email');
    this.profession = localStorage.getItem('profession');
    this.userId = localStorage.getItem('id');
    this.greeting = `Welcome ${this.name}`;
    this.title.setTitle(`${this.greeting} | Terminus`);
    this.searchForm.valueChanges.subscribe((value: any) => {
      this.userService.searchUser(value).subscribe({
        next: response => {
          if (response.status === 'success') {
            this.users = [];
            this.users = response.success;
          } else {
            this.snackBar.open(response.failure.error.message, 'Close');
          }
        },
        error: error => {
          console.log(error);
          this.snackBar.open('Internal Server Error', 'Close');
        }
      })
    });

    this.updateForm = this.formBuilder.group({
      'name': [this.name, Validators.required],
      'email': [this.email, Validators.required],
      'profession': [this.profession, Validators.required]
    });
  }


  deleteAccount() {
    let confirmation = confirm('Are you Sure?');
    if (confirmation) {
      this.userService.deleteAccoun(this.userId).subscribe({
        next: response => {
          if (response.status === 'success') {
            this.logout();
          } else {
            this.snackBar.open(response.failure.error.message, 'Close');
          }
        },
        error: error => {
          console.log(error);
          this.snackBar.open('Internal Server Error', 'Close');
        }
      })
    }
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/']);
  }

  update() {
    let user = {
      'id': localStorage.getItem('id'),
      'name': this.updateForm.get('name').value,
      'profession': this.updateForm.get('profession').value
    };

    this.updateForm.disable();
    this.inProgress = true;
    this.userService.updateUser(user).subscribe({
      next: response => {
        if (response.status === 'success') {
          this.updateForm.enable();
          this.inProgress = false;
          this.snackBar.open('Record Updated', 'Close');
          this.name = user.name;
          this.profession = user.profession;
          localStorage.removeItem('user');
          localStorage.removeItem('profession');
          localStorage.setItem('name', this.name);
          localStorage.setItem('profession', this.profession);
        } else {
          this.updateForm.enable();
          this.inProgress = false;
          this.snackBar.open(response.failure.error.message, 'Close');
        }
      },
      error: error => {
        console.log(error);
        this.snackBar.open('Internal Server Error', 'Close');
        this.updateForm.enable();
        this.inProgress = false;
      }
    })
  }
}

