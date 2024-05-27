package com.example.email.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.security.auth.login.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.email.domain.Message;

import lombok.Value;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;

	@Autowired
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(String to, String subject, String message) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		mailSender.send(simpleMailMessage);
		System.out.println("Email Has been sent Successfully!!");

	}

	@Override
	public void sendEmail(String[] to, String subject, String message) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		simpleMailMessage.setFrom("pandey1543146@gmail.com");
		mailSender.send(simpleMailMessage);
		System.out.println("Email Has been sent Successfully!!");

	}

	@Override
	public void sendEmailWithHtml(String to, String subject, String htmlContent) {

		MimeMessage simpleMailMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);
			helper.setFrom("pandey1543146@gmail.com");
			mailSender.send(simpleMailMessage);
			System.out.println("Email Has been sent Successfully!!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void sendEmailWithFile(String to, String subject, String message, File file) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(message);
			helper.setFrom("pandey1543146@gmail.com");

			FileSystemResource fileSystemResource = new FileSystemResource(file);
			helper.addAttachment(fileSystemResource.getFilename(), file);
			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email", e);
		}
	}

	@Override
	public void sendEmailWithFileWithStream(String to, String subject, String message, InputStream is) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(message, true);
			helper.setFrom("pandey1543146@gmail.com");

			File file = new File("test.png");
			Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

			FileSystemResource fileSystemResource = new FileSystemResource(file);
			helper.addAttachment(fileSystemResource.getFilename(), file);

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email", e);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@org.springframework.beans.factory.annotation.Value("${mail.store.protocol}")
	String protocol;
	@org.springframework.beans.factory.annotation.Value("${mail.imaps.host}")
	String host;
	@org.springframework.beans.factory.annotation.Value("${mail.imaps.port}")
	String port;

	@org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
	String username;

	@org.springframework.beans.factory.annotation.Value("${spring.mail.password}")
	String password;

	@Override
	public List<Message> getInboxMessages() {
		// code to receive all the email data
		Properties configurations = new Properties();
		configurations.setProperty("mail.store.protocol", protocol);
		configurations.setProperty("mail.imaps.host", host);
		configurations.setProperty("mail.imaps.port", port);
		Session session = Session.getDefaultInstance(configurations);
//				configurations.setProperty("", "");
		try {
			Store store = session.getStore();
			store.connect(username, password);
			System.out.println("EmailServiceImpl::getInboxMessages::store " + store);
			Folder inbox = store.getFolder("INBOX");
			System.out.println("EmailServiceImpl::getInboxMessages::inbox " + inbox);
			inbox.open(Folder.READ_ONLY);
			javax.mail.Message[] messages = inbox.getMessages();
			System.out.println("EmailServiceImpl::getInboxMessages::messages " + messages);
			List<Message> list = new ArrayList<>();

			for (javax.mail.Message message : messages) {
				System.out.println(message.getSubject());
				System.out.println("==================================");

				String content = getContentFromEmailMessage(message);
				System.out.println("EmailServiceImpl::getInboxMessages::content " + content);
				List<String> files = getfilesFromEmailMessage(message);
				System.out.println("EmailServiceImpl::getInboxMessages::files " + files);
				list.add(Message.builder().subject(message.getSubject()).content(content).files(files).build());
				System.out.println("EmailServiceImpl::getInboxMessages::list " + list);
			}

			return list;

		} catch (NoSuchProviderException e) {

			e.printStackTrace();
		} catch (MessagingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}

	private List<String> getfilesFromEmailMessage(javax.mail.Message message) throws MessagingException, IOException {

		System.out.println("EmailServiceImple::getfilesFromEmailMessage::message" + message);
		List<String> file = new ArrayList<>();
		if (message.isMimeType("multipart/*")) {
			Multipart content = (Multipart) message.getContent();
			System.out.println("EmailServiceImple::getfilesFromEmailMessage::content" + content);
			for (int i = 0; i < content.getCount(); i++) {
				BodyPart bodyPart = content.getBodyPart(i);
				System.out.println("EmailServiceImple::getfilesFromEmailMessage::bodyPart" + bodyPart);
				if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
					InputStream inputStream = bodyPart.getInputStream();
					System.out.println("EmailServiceImple::getfilesFromEmailMessage::inputStream" + inputStream);
					File file2 = new File("src/main/resources/email" + bodyPart.getFileName());
					System.out.println("EmailServiceImple::getfilesFromEmailMessage::file2" + file2);
					// saved the file
					Files.copy(inputStream, file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
					file.add(file2.getAbsolutePath());
					
				}
			}

		}

		return file;
	}

	private String getContentFromEmailMessage(javax.mail.Message message) throws MessagingException, IOException {
		System.out.println("EmailServiceImple::getContentFromEmailMessage::message" + message.toString());
		if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
			String content = (String) message.getContent();
			System.out.println("EmailServiceImple::getContentFromEmailMessage::content" + content.toString());
			return content;
		} else if (message.isMimeType("multipart/*")) {
			Multipart content = (Multipart) message.getContent();
			System.out.println("EmailServiceImple::getContentFromEmailMessage::else if::content" + content.toString());
			for (int i = 0; i < content.getCount(); i++) {
				BodyPart bodyPart = content.getBodyPart(i);
				System.out.println("EmailServiceImple::getContentFromEmailMessage::else if::bodyPart" + bodyPart.toString());
				if (bodyPart.isMimeType("text/plain")) {
					return (String) bodyPart.getContent();

				}
			}
		}
		return null;
	}

}
