/**
 * 
 */
export interface Product {
    id: number;
    name: string;
    info: string;
    price: number;
    quantity: number;
    imgSource: String;
    reviews: Map<String,Number>;
}