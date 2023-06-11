import { ServiceabilityModel } from "./serviceability";

export interface ProductModel {
    pid: number,
    pname: string,
    brand: string,
    description: string,
    price: number,
    imageUrl: string,
    serviceability: ServiceabilityModel[]
}