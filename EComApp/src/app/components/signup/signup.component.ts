import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserApiService } from 'src/app/shared/services/api/user-api.service';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit, OnDestroy {
    subscription: Subscription = Subscription.EMPTY;
    hidePassword = true;
    hideConfirmPassword = true;
    signupForm: FormGroup;
    signupError: any = { username: '', password: '', confirmPassword: '' };
    showSignupSpinner: boolean = false;

    navLinks = [
        { path: '../login', label: 'Login' },
        { path: '', label: 'Signup' },
    ];

    constructor(private fb: FormBuilder, private userApiService: UserApiService, private router: Router) {
        // Create new Signup Form
        this.signupForm = this.fb.group({
            uname: ['', [Validators.required, Validators.pattern(/^[a-zA-Z ]+$/)]],
            username: ['', [Validators.required, Validators.pattern(/^\w+$/)]],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        // navigate to home if user is already loggedin
        if (localStorage.getItem('user')) {
            this.router.navigate(['/']);
        }
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    // Method to match password & confirm Password
    matchPassword(value: string) {
        const password = this.signupForm.controls['password'].value;
        if (password === value) {
            this.signupError['confirmPassword'] = '';
            this.signupForm.controls['confirmPassword'].setErrors(null);
        } else {
            this.signupError['confirmPassword'] = 'Password Does Not Matches!';
            this.signupForm.controls['confirmPassword'].setErrors({ invalid: true });
        }
    }

    // Method to handle Signup Form Submit Event
    onSubmit() {
        if (this.signupForm.valid) {
            this.registerUser();
        }
    }

    // Method to register User
    registerUser() {
        // get form value from Signup Form
        let formValue = this.signupForm.value;
        // Delete Confirm Password field from signup form
        delete formValue['confirmPassword'];
        // Show Signup Spinner
        this.showSignupSpinner = true;

        this.subscription = this.userApiService.signupUser(formValue).subscribe({
            next: (resp) => {
                if (resp) {
                    if (resp.status === 'success' && resp.status_code == 201) {
                        // User Registration Successfull
                        this.router.navigate(['../auth/login']);
                    } else if (resp.status == 'error' && resp.status_code == 409 && resp.error) {
                        this.signupError[resp.error.error_field] = resp.error.message;
                        this.signupForm.controls[resp.error.error_field].setErrors({ invalid: true });
                    } else {
                        alert("Some Error Occurred!");
                    }
                } else {
                    alert("Some Error Occurred!");
                }
            },
            error: (err) => {
                console.error(err);
                alert("Internal Server Error!");
            },
            complete: () => {
                // Hide Signup Spinner
                this.showSignupSpinner = false;
            }
        })
    }
}
