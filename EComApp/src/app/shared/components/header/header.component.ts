import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { NavigationEnd, NavigationStart, ResolveEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DataService } from '../../services/api/data.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
    showDrawer: boolean = false;
    showDrawerBtn: boolean = false;
    subscriptions: Subscription[] = [];
    username: string = '';

    // Constructor
    constructor(private dataService: DataService, private router: Router) {

        this.subscriptions.push(this.router.events.subscribe((event => {
            if (event instanceof NavigationEnd) {
                // Check if the url is dashboard,
                //if so then display the drawer button,
                //else hide the drawer button
                if (event.urlAfterRedirects === '/dashboard') {
                    this.showDrawerBtn = true;
                    this.subscriptions.push(this.dataService.currentState.subscribe((open) => { this.showDrawer = open }));
                }
                else
                    this.showDrawerBtn = false;
            }
        })));
    }
    // Get Current User from localStorage
    getUser() {
        // ser username
        if (localStorage.getItem('user'))
            this.username = JSON.parse(localStorage.getItem('user')!).username;
        return localStorage.getItem('user');
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
        // Unsubscribe all subscriptions
        this.subscriptions.forEach((subscription) => { subscription.unsubscribe });
    }

    // Method to navigate to home 
    navigateToHome() {
        this.router.navigate(['/']);
    }

    // Method to logout User
    logoutUser() {
        localStorage.clear();
        this.router.navigate(['auth/login']);
    }

    // Method to redirect user to login Page
    loginUser() {
        this.router.navigate(['auth/login']);
    }

    // Method to toggle Sidenav
    toggleDrawer() {
        this.showDrawer = !this.showDrawer;
        this.dataService.changeState(this.showDrawer);
    }
}
