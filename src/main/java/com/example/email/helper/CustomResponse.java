package com.example.email.helper;

import org.springframework.http.HttpStatus;

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
public class CustomResponse {
	
	private String message;
	
	private HttpStatus httpStatus;
	
	private boolean success;
	
	
	

}
