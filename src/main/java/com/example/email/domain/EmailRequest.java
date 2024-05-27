package com.example.email.domain;

import org.springframework.http.HttpStatus;

import com.example.email.helper.CustomResponse;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Getter
@Setter
@Builder
public class EmailRequest {

	private String to;
	private String subject;
	private String message;

//	public EmailRequest() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public EmailRequest(String to, String subject, String message) {
//		super();
//		this.to = to;
//		this.subject = subject;
//		this.message = message;
//	}
//
//	public String getTo() {
//		return to;
//	}
//
//	public void setTo(String to) {
//		this.to = to;
//	}
//
//	public String getSubject() {
//		return subject;
//	}
//
//	public void setSubject(String subject) {
//		this.subject = subject;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	@Override
//	public String toString() {
//		return "EmailRequest [to=" + to + ", subject=" + subject + ", message=" + message + "]";
//	}

}
