import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Product } from './product';
import { User } from './user';


@Injectable({ providedIn: 'root' })
export class ProductService {

  private productsUrl = 'http://localhost:8080/products';  // URL to web api
  private cartUrl = 'http://localhost:8080/users/cart/'; //URL to web api for cart things

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
    ) { }

  /** GET products from the server */
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productsUrl)
      .pipe(
        catchError(this.handleError<Product[]>('getProducts', []))
      );
  }

  /** GET product by id. Will 404 if id not found */
  getProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;
    return this.http.get<Product>(url).pipe(
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /* GET products whose name contains search term */
  searchProducts(term: string): Observable<Product[]> {
    if (!term.trim()) {
      // if not search term, return empty product array.
      return of([]);
    }
    return this.http.get<Product[]>(`${this.productsUrl}/?name=${term}`).pipe(
      catchError(this.handleError<Product[]>('searchProducts', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new product to the server */
  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.productsUrl, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /** DELETE: delete the product from the server */
  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;

    return this.http.delete<Product>(url, this.httpOptions).pipe(
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }

  /** PUT: update the product on the server */
  updateProduct(product: Product): Observable<any> {
    return this.http.put(this.productsUrl, product, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  addToCart(user: User, product: Product){
    let url = '?uid=' + user.id + '&pid=' + product.id;
    return this.http.put<any>(this.cartUrl+url, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  getReviews(product: Product) {
    return this.http.get<Map<String,Number>>(this.productsUrl + "/" + product.id +
                               "/reviews", this.httpOptions);
  }

  addReview(user: User, product: Product, rating: number) {
    return this.http.post<Map<String,Number>>(this.productsUrl + "/" + product.id +
                               "/reviews/?uid=" + user.id + "&rating=" + rating,
                               this.httpOptions);
  }

  editReview(user: User, product: Product, rating: number) {
    return this.http.put<Map<String,Number>>(this.productsUrl + "/" + product.id +
                               "/reviews/?uid=" + user.id + "&rating=" + rating,
                               this.httpOptions);
  }

  deleteReview(user: User, product: Product) {
    return this.http.delete<Map<String,Number>>(this.productsUrl + "/" + product.id +
                               "/reviews/?uid=" + user.id,
                               this.httpOptions);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
