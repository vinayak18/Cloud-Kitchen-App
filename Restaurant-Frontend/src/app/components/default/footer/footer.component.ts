import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { feedback } from '../../../models/feedback';
import { FeedbackService } from 'src/app/services/feedback/feedback.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
})
export class FooterComponent implements OnInit {
  constructor(private feedbackService: FeedbackService) {}
  feedbackForm: FormGroup;
  ngOnInit(): void {
    this.feedbackForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
        // Validators.pattern('^w+([.-]?w+)@w+([.-]?w+)(.w{2,3})+$'),
      ]),
      message: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
      ]),
    });
  }

  sendFeedback() {
    const feedbackData = new feedback(
      this.feedbackForm.get('name').value,
      this.feedbackForm.get('email').value,
      0,
      0,
      this.feedbackForm.get('message').value,
      false
    );
    console.log(feedbackData);
    this.feedbackService.addNewFeedback(feedbackData).subscribe((data) => {
      console.log(data);
    });
  }
}
