import { AvailabilityModel } from "./availability";
import { ErrorResponseModel } from "./error";

export interface AvailabilityResponseModel {
    status: string,
    status_code: number,
    data?: AvailabilityModel,
    error?: ErrorResponseModel
}