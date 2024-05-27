package com.example.email.domain;

import java.util.List;

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
public class Message {
	private String from;
	private String content;
	private List<String> files;
	private String subject;
	

}
