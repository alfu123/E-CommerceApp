import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { SignupComponent } from './components/signup/signup.component';
import { UserGuard } from './shared/gaurds/user.guard';

const routes: Routes = [
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'auth/login', component: LoginComponent },
    { path: 'auth/signup', component: SignupComponent },
    { path: 'products', canActivate: [UserGuard], component: ProductDetailsComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
