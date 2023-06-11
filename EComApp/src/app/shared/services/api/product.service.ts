import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiResponseModel } from '../../models/api-response';
import { AvailabilityResponseModel } from '../../models/availability-response';

@Injectable({
    providedIn: 'root'
})
export class ProductService {
    baseApiUrl: string = environment.baseApiUrl;

    constructor(private httpClient: HttpClient) { }

    // Get All Products from API
    getAllProducts() {
        return this.httpClient.get<ApiResponseModel>(`${this.baseApiUrl}/products`);
    }

    // Get A single product ffrom product id
    getProductById(pid: number) {
        return this.httpClient.get<ApiResponseModel>(`${this.baseApiUrl}/products?pid=${pid}`);
    }

    // Get availability of product with pincode 
    getAvailability(pid: number, pincode: number) {
        return this.httpClient.get<AvailabilityResponseModel>(`${this.baseApiUrl}/products/product?pid=${pid}&pincode=${pincode}`);
    }
}
