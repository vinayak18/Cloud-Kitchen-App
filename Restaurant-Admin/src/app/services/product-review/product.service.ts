import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { urls } from '../apiUrls';
import { Observable } from 'rxjs';
import { product } from 'src/app/models/product';

@Injectable({
  providedIn: 'root'
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

  getProductById(pid: string): Observable<product> {
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

  getProductsByFoodType(type: string): Observable<product[]> {
    const url = urls.productUrls.byFoodType.replace("{foodtype}", type);
    return this.http.get<product[]>(url);
  }

  changeLiveStatus(pid: number) {
    const url = urls.productUrls.changeLiveStatus.replace("{pid}", '' + pid);
    return this.http.put(url, null);
  }

  updateProduct(productDetails: product) {
    const url = urls.productUrls.update;
    return this.http.put(url, productDetails);
  }

  deleteProduct(pId: number) {
    const url = urls.productUrls.delete.replace("{pid}", '' + pId);
    return this.http.delete(url);
  }

}
