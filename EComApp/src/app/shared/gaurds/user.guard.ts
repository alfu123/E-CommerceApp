import { Injectable, OnDestroy } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, ResolveEnd, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, Subscription } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserGuard implements CanActivate {

    constructor(private router: Router) {

    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
            
        if (localStorage.getItem('user')) {
            return true;
        }

        this.router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
        return false;
    }

}
