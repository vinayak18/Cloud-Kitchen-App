import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { urls } from 'src/app/services/apiUrls';
import { product } from 'src/app/models/product';
import { CartItemsInfo } from 'src/app/models/cartItemsInfo';
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) { }

  //product service apis
  getBestSellers(pid: number): Observable<product[]> {
    const url = urls.productUrls.bestseller.replace('{pid}', '' + pid);
    return this.http.get<product[]>(url);
  }

  getAllProducts(): Observable<product[]> {
    const url = urls.productUrls.all;
    return this.http.get<product[]>(url);
  }

  getProductById(pid: number): Observable<product> {
    const url = urls.productUrls.byId.replace('{pid}', '' + pid);
    return this.http.get<product>(url);
  }

  getProductByFoodType(foodType: string): Observable<product[]> {
    const url = urls.productUrls.byFoodType.replace(
      '{foodtype}',
      '' + foodType
    );
    return this.http.get<product[]>(url);
  }

  getMultiProductById(cartItemsInfo: CartItemsInfo[]): Observable<product[]> {
    const url = urls.productUrls.multipleProducts;
    return this.http.post<product[]>(url, cartItemsInfo);
  }
}
