package com.restaurant.messagingservice.model;

import java.util.HashMap;
import java.util.Map;

public class MessagingConfig {
	public final static String SUBJECT = "TweetApp: OTP Verification";
	public final static String HOST_NAME = "mail.smtp.host";
	public final static String HOST_URL = "smtp.gmail.com";
	public final static String PORT = "mail.smtp.port";
	public final static String PORT_NUMBER = "465";
	public final static String SSL_URL = "mail.smtp.ssl.enable";
	public final static String AUTH_URL = "mail.smtp.auth";
	public final static String TRUE = "true";
	public static Map<String,String> OTP = new HashMap<>();
}
