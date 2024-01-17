import { foodType } from './foodType';

export class product {
  pid: number;
  name: string;
  desc: string;
  price: number;
  quantity: number;
  avgRating: number;
  img_url: string[];
  type: foodType;
  category: string;
  live: boolean;
  constructor(
    $pid: number,
    $name: string,
    $desc: string,
    $price: number,
    $quantity: number,
    $avgRating: number,
    $img_url: string[],
    $type: foodType,
    $category: string,
    $live: boolean
  ) {
    this.pid = $pid;
    this.name = $name;
    this.desc = $desc;
    this.price = $price;
    this.quantity = $quantity;
    this.avgRating = $avgRating;
    this.img_url = $img_url;
    this.type = $type;
    this.category = $category;
    this.live = $live;
  }
}
