import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { foodType } from 'src/app/models/foodType';
import { order } from 'src/app/models/order';
import { status } from 'src/app/models/status';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { OrderService } from 'src/app/services/user-coupon-order/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
})
export class OrderComponent implements OnInit {
  status: status[] = [status.ACCEPTED, status.CANCELLED, status.DECLINED, status.DELIVERED, status.DISPATCHED, status.PLACED, status.PREPARING];
  orderType: string = '';
  orders: order[] = [];
  isLoaded: boolean = false;

  constructor(private router: Router, private route: ActivatedRoute, private orderService: OrderService, private loader: ScreenLoaderService) { }

  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.route.params.subscribe((val: Params) => {
      this.orderType = val['status'];
      console.log(this.orderType);
      this.getAllOrdersByStatus(this.orderType);
    });
  }

  changeStatus(order: order, status: status) {
    this.orderService.updateOrderStatus(order.orderId, status).subscribe((data) => {
      order.status = status;
    });
  }

  getAllOrdersByStatus(status: string) {
    this.orderService.getAllOrdersByStatus(status).subscribe((orders: order[]) => {
      this.orders = orders;
      console.log(this.orders);
    });

  }
}
