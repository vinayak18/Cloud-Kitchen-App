import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { urls } from '../apiUrls';
import { order } from 'src/app/models/order';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private http: HttpClient) { }

  initiateStripePayment(payment: any): Observable<any> {
    const url = urls.userUrls.initiateStripePayment;
    return this.http.post(url, payment);
  }

  initiateRazorPayPayment(payment: any): Observable<any> {
    const url = urls.userUrls.initiateRazorPayPayment;
    return this.http.post(url, payment);
  }

  getOrderById(orderId: string): Observable<any> {
    const url = urls.userUrls.byOrderId.replace('{id}', orderId);
    return this.http.get(url);
  }

  getActiveOrders(userId: string): Observable<any> {
    const url = urls.userUrls.activeOrders.replace('{userId}', userId);
    return this.http.get(url);
  }

  getPastOrders(userId: string): Observable<any> {
    const url = urls.userUrls.pastOrders.replace('{userId}', userId);
    return this.http.get(url);
  }

  getAllOrdersByStatus(status: string): Observable<order[]> {
    const url = urls.userUrls.allOrdersByStatus.replace('{status}', status);
    return this.http.get<order[]>(url);
  }

  updateOrderRating(orderId: string, rating: number): Observable<any> {
    const url = urls.userUrls.updateOrderRating
      .replace('{id}', orderId)
      .replace('{rating}', '' + rating);
    return this.http.put(url, null);
  }

  updateOrderStatus(orderId: string, status: string): Observable<any> {
    const url = urls.userUrls.updateOrderStatus
      .replace('{id}', orderId)
      .replace('{status}', '' + status);
    return this.http.put(url, null);
  }

  addNewOrder(order: order): Observable<any> {
    const url = urls.userUrls.addNewOrder;
    return this.http.post(url, order);
  }

  calculateDistanceMatrix(distanceMatrix: any): Observable<any> {
    const url = urls.userUrls.calculateDistance;
    return this.http.post(url, distanceMatrix);
  }
}
