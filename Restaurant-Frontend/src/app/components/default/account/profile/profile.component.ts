import { HttpClient } from '@angular/common/http';
import { Component, ViewChild, ElementRef } from '@angular/core';
import { UserService } from 'src/app/services/user-coupon-order/user.service';
import { userDetails } from '../../../../models/userDetails';
import { address } from 'src/app/models/address';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {
  @ViewChild('phoneNo') phoneNo: ElementRef;
  currUser: userDetails;
  primaryAddress: address;
  currentAddress: address;
  isEditable: boolean = false;

  constructor(private userService: UserService) {}
  ngOnInit(): void {
    this.currUser = this.userService.getCurrentUserDetails();
    if (this.currUser.address.length > 0) this.setPrimaryAddress();
  }
  saveChanges() {
    this.isEditable = !this.isEditable;
    this.currUser.phoneNo = this.phoneNo.nativeElement.value;
    if (this.primaryAddress !== this.currentAddress) {
      this.currentAddress.active = false;
      this.primaryAddress.active = true;
    }
    this.userService.updateUser(this.currUser).subscribe((data) => {
      this.userService.setUserDetails(data);
    });
  }
  setPrimaryAddress() {
    for (let address of this.currUser.address) {
      if (address.active) {
        this.primaryAddress = address;
        this.currentAddress = address;
        break;
      }
    }
  }
}
