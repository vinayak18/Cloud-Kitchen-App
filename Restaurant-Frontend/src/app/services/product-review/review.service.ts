import { urls } from './../apiUrls';
import { review } from '../../models/review';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { userReview } from 'src/app/models/userReview';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  constructor(private http: HttpClient) {}

  getReviewByPID(pid: number): Observable<review> {
    const url = urls.reviewUrls.byId.replace('{pid}', '' + pid);
    return this.http.get<review>(url);
  }

  addNewReviewByPID(pid: number, review: userReview): Observable<review> {
    const url = urls.reviewUrls.add.replace('{pid}', '' + pid);
    return this.http.post<review>(url, review);
  }
}
