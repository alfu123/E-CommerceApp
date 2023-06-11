import { ErrorResponseModel } from "./error";
import { ProductModel } from "./product";

export interface ApiResponseModel {
    status: string,
    status_code: number,
    data: ProductModel[],
    error?: ErrorResponseModel
}