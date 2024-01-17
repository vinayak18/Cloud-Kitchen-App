import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { feedback } from '../../../models/feedback';
import { FeedbackService } from 'src/app/services/feedback/feedback.service';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css'],
})
export class FeedbackComponent {
  feedbackForm: FormGroup;

  constructor(private feedbackService: FeedbackService) {}

  ngOnInit(): void {
    this.feedbackForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(25),
        // Validators.pattern('^w+([.-]?w+)@w+([.-]?w+)(.w{2,3})+$'),
      ]),
      phoneNumber: new FormControl('', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(10),
        Validators.pattern('[0-9]{10}'),
      ]),
      rating: new FormControl('', [Validators.required]),
      message: new FormControl('', [Validators.required]),
    });
  }

  sendFeedback() {
    const feedbackData = new feedback(
      this.feedbackForm.get('name').value,
      this.feedbackForm.get('email').value,
      this.feedbackForm.get('phoneNumber').value,
      this.feedbackForm.get('rating').value,
      this.feedbackForm.get('message').value,
      false
    );
    console.log(feedbackData);
    this.feedbackService.addNewFeedback(feedbackData).subscribe((data) => {
      console.log(data);
    });
  }
}
