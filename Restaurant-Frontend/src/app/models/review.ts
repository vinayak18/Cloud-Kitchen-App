import { userReview } from './userReview';

export class review {
  reviewId: number;
  pid: number;
  totalRating: number;
  noOfRating: number;
  userReview: userReview[];

  constructor(
    $reviewId: number,
    $pid: number,
    $totalRating: number,
    $noOfRating: number,
    $userReview: userReview[]
  ) {
    this.reviewId = $reviewId;
    this.pid = $pid;
    this.totalRating = $totalRating;
    this.noOfRating = $noOfRating;
    this.userReview = $userReview;
  }
}
