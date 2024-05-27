package com.example.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionMessage.ItemsBuilder;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.email.domain.Message;
import com.example.email.service.EmailService;

@SpringBootTest
public class EmailSenderTest {

	
	@Autowired
	private EmailService emailService;
	
//	@Test
//	void emailSendTest() {
//		System.out.println("Sending Email");
//		emailService.sendEmail("pandey1543146@gmail.com", "This message from spring boot application", "This email is send uding spring boot");
//	}
	
	
	
//	@Test
//	void sendHtmlInEmail() {
//		
//		String html="" + "<h1 style='color:red;border:1px solod red;'> Welcome to mumbai vikas pandey </h1>" + "";
//		emailService.sendEmailWithHtml("pandey1543146@gmail.com","Email from Spring boot", html);
//	}
	
//	@Test
//	void sendHtmlInFile() {
//		
//		String html="" + "<h1 style='color:red;border:1px solod red;'> Welcome to mumbai vikas pandey </h1>" + "";
//		emailService.emailSendWithFile("pandey1543146@gmail.com","Email with file", "This email contains file",new File(html));
//	}
	
//	@Test
//	void sendEmailWithFileWithStream() {
//		
//		
//		File file=new File("C:\\Users\\vikas\\Documents\\workspace-spring-tool-suite-4-4.22.0.RELEASE\\EmailProject\\src\\main\\resources\\static\\vikas.png");
//		System.out.println("sendEmailWithFileWithStream::File ===== " + file);
//		try {
//			InputStream iStream=new FileInputStream(file);
//			System.out.println("sendEmailWithFileWithStream::iStream ===== " + iStream);
//			emailService.sendEmailWithFileWithStream(
//					"pandey1543146@gmail.com",
//					"Email with file",
//					"This email contains file",
//					iStream);
//		}  catch (FileNotFoundException e) {
//			System.out.println("Exception Caught!!!" );
//			e.printStackTrace();
//		}
//		
//	}
	
	
	// receiving email test
	@Test
	void getInbox() {
		
		List<Message> inboxMessage = emailService.getInboxMessages();
		
		inboxMessage.forEach(item ->{
			System.out.println(item.getSubject());
			System.out.println(item.getContent());
			System.out.println(item.getFiles());
			System.out.println("****************************************");
		});
	}
	
	
}
