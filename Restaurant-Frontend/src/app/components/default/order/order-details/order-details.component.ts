import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { foodType } from 'src/app/models/foodType';
import { order } from 'src/app/models/order';
import { product } from 'src/app/models/product';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { OrderService } from 'src/app/services/user-coupon-order/order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css'],
})
export class OrderDetailsComponent implements OnInit {
  orderId: string;
  header_flag: number[] = [0, 0, 0, 0];
  breakfastList: product[] = [];
  lunchList: product[] = [];
  dinnerList: product[] = [];
  specialDishList: product[] = [];
  order: order = {} as order;
  discountAmount: number = 160.0;
  isLoaded: boolean = false;

  constructor(
    private orderService: OrderService,
    private activeRoute: ActivatedRoute,
    private loader: ScreenLoaderService,
    private router: Router
  ) {
    this.orderId = this.activeRoute.snapshot.params['orderId'];
    console.log(this.orderId);
  }

  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.orderService.getOrderById(this.orderId).subscribe((data) => {
      this.order = data;
      console.log(data);
      this.breakfastList = this.order.orderDetails.filter(
        (value) => value.type === foodType.BREAKFAST
      );
      this.lunchList = this.order.orderDetails.filter(
        (value) => value.type === foodType.LUNCH
      );
      this.dinnerList = this.order.orderDetails.filter(
        (value) => value.type === foodType.DINNER
      );
      this.specialDishList = this.order.orderDetails.filter(
        (value) => value.type === foodType.SPECIAL_DISH
      );
    });
  }

  backToPrevPage() {
    if (this.activeRoute.snapshot.routeConfig.path.includes('/past/')) {
      this.router.navigateByUrl('/order/past');
    } else {
      this.router.navigateByUrl('/order/active');
    }
  }
}
