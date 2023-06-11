import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, ResolveEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserApiService } from 'src/app/shared/services/api/user-api.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
    hidePassword = true;
    loginForm: FormGroup;
    loginError: any = { username: '', password: '' };
    subscriptions: Subscription[] = [];
    returnUrl: string = '/';
    showLoginSpinner: boolean = false;

    navLinks = [
        { path: '', label: 'Login' },
        { path: '../signup', label: 'Signup' },
    ];

    constructor(private fb: FormBuilder, private userApiService: UserApiService, private route: ActivatedRoute, private router: Router) {
        // Create new Login Form
        this.loginForm = this.fb.group({
            username: ['', [Validators.required, Validators.pattern(/^\w+$/)]],
            password: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        //navigate to home if user is already logged in
        if (localStorage.getItem('user')) {
            this.router.navigate(['/']);
        }
        // get return URL from current URL
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'];
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach((subscription) => { subscription.unsubscribe() });
    }

    onSubmit() {
        if (this.loginForm.valid) {
            this.verifyUser();
        }
    }

    verifyUser() {
        // Show Login Spinner
        this.showLoginSpinner = true;
        // login user using User Api
        this.subscriptions.push(this.userApiService.loginUser(this.loginForm.value).subscribe({
            next: (resp) => {
                if (resp) {
                    if (resp.status === 'success' && resp.status_code == 200) {
                        //login Success
                        localStorage.setItem("user", JSON.stringify(resp.data));
                        this.router.navigateByUrl(this.returnUrl);
                    } else if (resp.status === 'error' && resp.error && resp.status_code == 401) {
                        // Set Errors
                        this.loginError[resp.error.error_field] = resp.error?.message;
                        this.loginForm.controls[resp.error.error_field].setErrors({ invalid: true });
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
                // Hide Login Spinner
                this.showLoginSpinner = false;
            }
        }));
    }
}

