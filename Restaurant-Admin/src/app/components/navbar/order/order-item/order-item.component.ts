import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { foodType } from 'src/app/models/foodType';
import { order } from 'src/app/models/order';
import { status } from 'src/app/models/status';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { OrderService } from 'src/app/services/user-coupon-order/order.service';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.scss', '../order.component.scss']
})
export class OrderItemComponent implements OnInit {
  orderId: string = '';
  orderDetails: order;
  breakfastList = [];
  lunchList = [];
  dinnerList = [];
  specialDishList = [];
  isLoaded: boolean = false;
  status: status[] = [status.ACCEPTED, status.CANCELLED, status.DECLINED, status.DELIVERED, status.DISPATCHED, status.PLACED, status.PREPARING];

  constructor(private router: Router, private route: ActivatedRoute, private loader: ScreenLoaderService, private orderService: OrderService) { }
  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.route.params.subscribe((val: Params) => {
      this.orderId = val['orderid'];
      this.getOrderById(this.orderId);
    });
  }

  getOrderById(id: string) {
    this.orderService.getOrderById(id).subscribe((data: order) => {
      this.orderDetails = data;
      this.breakfastList = this.orderDetails.orderDetails.filter(
        (value) => value.type === foodType.BREAKFAST
      );
      this.lunchList = this.orderDetails.orderDetails.filter(
        (value) => value.type === foodType.LUNCH
      );
      this.dinnerList = this.orderDetails.orderDetails.filter(
        (value) => value.type === foodType.DINNER
      );
      this.specialDishList = this.orderDetails.orderDetails.filter(
        (value) => value.type === foodType.SPECIAL_DISH
      );
    });
  }

  changeStatus(status: status) {
    this.orderService.updateOrderStatus(this.orderDetails.orderId, status).subscribe((data) => {
      this.orderDetails.status = status;
    });
  }
}
