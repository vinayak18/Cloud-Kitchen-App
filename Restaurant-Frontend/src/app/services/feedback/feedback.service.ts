import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { urls } from '../apiUrls';
import { feedback } from 'src/app/models/feedback';

@Injectable({
  providedIn: 'root',
})
export class FeedbackService {
  constructor(private http: HttpClient) {}

  addNewFeedback(feedback: feedback): Observable<any> {
    const url = urls.feedbackUrls.add;
    return this.http.post(url, feedback);
  }

  getCustomerTestimony(): Observable<feedback[]> {
    const url = urls.feedbackUrls.testimony;
    return this.http.get<feedback[]>(url);
  }
}
