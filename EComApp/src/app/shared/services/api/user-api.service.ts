import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiResponseModel } from '../../models/api-response';

@Injectable({
    providedIn: 'root'
})
export class UserApiService {
    baseApiUrl = environment.baseApiUrl;

    constructor(private httpClient: HttpClient) { }

    // Login User with API
    loginUser(object: Object) {
        return this.httpClient.post<ApiResponseModel>(`${this.baseApiUrl}/auth/login`, object);
    }
    // Signup User
    signupUser(object: Object) {
        return this.httpClient.post<ApiResponseModel>(`${this.baseApiUrl}/auth/signup`, object);
    }
}
