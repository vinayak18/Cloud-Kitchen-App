export class feedback {
  name: string;
  email: string;
  phoneNo: number;
  restaurantRating: number;
  message: string;
  active: boolean;
  constructor(
    $name: string,
    $email: string,
    $phoneNo: number,
    $restaurantRating: number,
    $message: string,
    $active: boolean
  ) {
    this.name = $name;
    this.email = $email;
    this.phoneNo = $phoneNo;
    this.restaurantRating = $restaurantRating;
    this.message = $message;
    this.active = $active;
  }
}
