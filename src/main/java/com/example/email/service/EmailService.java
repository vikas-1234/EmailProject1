package com.example.email.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.email.domain.Message;

public interface EmailService {
	// send email to single person

	void sendEmail(String to, String subject, String message);

	// send email to multiple person

	void sendEmail(String[] to, String subject, String message);

	// send email with html
	void sendEmailWithHtml(String to, String subject,String htmlContent);
	
	//void email send with file
	
	void sendEmailWithFile(String to, String subject,String message, File file);
//	void emailSendWithFile(String to, String subject, String message, InputStream fileInputStream) throws IOException;
	
	void sendEmailWithFileWithStream(String to, String subject,String message, InputStream is);
	
	List<Message> getInboxMessages();
	
}
