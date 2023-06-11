import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatGridListModule } from '@angular/material/grid-list'
import { MatSidenavModule } from '@angular/material/sidenav';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { DataService } from './shared/services/api/data.service';

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        LoginComponent,
        SignupComponent,
        DashboardComponent,
        ProductDetailsComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        FlexLayoutModule,
        MatCardModule,
        MatInputModule,
        ReactiveFormsModule,
        MatTabsModule,
        HttpClientModule,
        MatGridListModule,
        MatSidenavModule,
        NgxSliderModule,
        MatDividerModule,
        MatExpansionModule,
        MatCheckboxModule,
        MatProgressSpinnerModule
    ],
    providers: [DataService],
    bootstrap: [AppComponent]
})
export class AppModule { }
