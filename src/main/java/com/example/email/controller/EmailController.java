package com.example.email.controller;


import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.email.domain.EmailRequest;
import com.example.email.helper.CustomResponse;
import com.example.email.service.EmailService;

@RestController
@RequestMapping("api/v1/email")
@CrossOrigin("*")
public class EmailController {
	

	private EmailService emailService;

	
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@PostMapping("/send")
	public ResponseEntity<CustomResponse> sendEmail(@RequestBody EmailRequest emailRequest){
		 emailService.sendEmailWithHtml(emailRequest.getTo(),emailRequest.getSubject(), emailRequest.getMessage());
		
		return ResponseEntity.ok(CustomResponse.builder().message("Email Sent Successfully!!!!").httpStatus(HttpStatus.OK).success(true).build());
	}
	
	@PostMapping("/send-with-body")
	public ResponseEntity<CustomResponse> sendWithFile(@RequestPart EmailRequest emailRequest, @RequestPart MultipartFile file ) throws IOException {
		 emailService.sendEmailWithFileWithStream(emailRequest.getTo(),emailRequest.getSubject(), emailRequest.getMessage(), file.getInputStream());
		
		return ResponseEntity.ok(CustomResponse.builder().message("Email Sent Successfully!!!!").httpStatus(HttpStatus.OK).success(true).build());
	}
	
	

}
