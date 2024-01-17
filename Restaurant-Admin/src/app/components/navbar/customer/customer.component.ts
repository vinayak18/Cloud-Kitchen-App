import { Component, OnInit } from '@angular/core';
import { userDetails } from 'src/app/models/userDetails';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { UserService } from 'src/app/services/user-coupon-order/user.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss'],
})
export class CustomerComponent implements OnInit {
  customers: userDetails[] = [];
  isLoaded: boolean = false;
  constructor(private userService: UserService, private loader: ScreenLoaderService) { }
  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    })
    this.getAllCustomers();
  }
  async getAllCustomers() {
    await this.userService.getAllCustomers().then((data: userDetails[]) => {
      this.customers = data;
    });
    await this.blobToImgUrl();
  }
  blobToImgUrl(): Promise<boolean> {
    for (let customer of this.customers) {
      if (customer.blobImage != null && customer.blobImage != undefined) {
        customer.img_url = 'data:image/jpeg;base64,' + customer.blobImage.picByte;
        console.log(customer.img_url);
      }
    }
    return new Promise((res, rej) => res(true));
  }
}
