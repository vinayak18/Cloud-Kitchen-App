package com.restaurant.messagingservice.serviceimpl;


import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurant.messagingservice.exception.EmailConfigException;
import com.restaurant.messagingservice.exception.InvalidOTPException;
import com.restaurant.messagingservice.model.CustomSucccessResponse;
import com.restaurant.messagingservice.model.MessagingConfig;
import com.restaurant.messagingservice.service.MessagingService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class MessagingServiceImpl implements MessagingService {
	
	private CustomSucccessResponse response;
	
	@Value("${twilio.sid}")
    private String twilioSID;
	
	@Value("${twilio.authToken}")
    private String twilioAuthToken;
	
	@Value("${twilio.origin}")
    private String twilioPhoneNo;
	
	@Value("${smtp.email}")
    private String smtpEmail;
	
	@Value("${smtp.password}")
    private String smtpPassword;

	@Override
	public ResponseEntity<Object> sendSMS(String phoneNo, int otp) {
		// TODO Auto-generated method stub
		Twilio.init(twilioSID, twilioAuthToken);
        Message.creator(new PhoneNumber("+91"+phoneNo),
                        new PhoneNumber(twilioPhoneNo), "Dear Customer 📞, OTP to reset your password is: "+otp+". Please do not share with anyone.").create();
        MessagingConfig.OTP.put(phoneNo, String.valueOf(otp));
        response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "OTP Sent Successfully");
        return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	private MimeMessage getEmailConfig() {
		// variable for gmail host

		// get the system properties
		Properties props = System.getProperties();
		System.out.println("Properties: " + props);

		// setting important information to properties object

		// host set
		props.put(MessagingConfig.HOST_NAME, MessagingConfig.HOST_URL);
		props.put(MessagingConfig.PORT, MessagingConfig.PORT_NUMBER);
		props.put(MessagingConfig.SSL_URL, MessagingConfig.TRUE);
		props.put(MessagingConfig.AUTH_URL, MessagingConfig.TRUE);

		// Step 1: to get the session object ...
		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpEmail, smtpPassword);
			}

		});

		session.setDebug(true);

		// Step 2: compose the message [text, multi-media]
		return new MimeMessage(session);
	}
	
	@Override
	public ResponseEntity<Object> sendEmail(String email, int otp) throws EmailConfigException {
		
		MimeMessage mimeMessage = getEmailConfig();

		try {
			// from email
			mimeMessage.setFrom(smtpEmail);

			// adding receipt to message
			mimeMessage.setRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(email));

			// adding to subject to message
			mimeMessage.setSubject(MessagingConfig.SUBJECT);

			// adding text to message
			// mimeMessage.setText(otp);
			mimeMessage.setContent("<div style='font-size: 18px'>Please use <b>" + otp
					+ "</b> as OTP to login to your tweet acount.</div>", "text/html");

			// send

			// Step 3: send the message using transport class
			Transport.send(mimeMessage);

			MessagingConfig.OTP.put(email, String.valueOf(otp));

			System.out.println("Sent successfully ...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new EmailConfigException("Invalid Email Configuration.");
		}
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "OTP Send Successful");
        return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> verifyOTP(String email, String otp) throws InvalidOTPException {		
		if (!MessagingConfig.OTP.get(email).equals(otp)) {
			throw new InvalidOTPException("Invalid OTP.");
		} 
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "OTP Verification Successful");
        return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
