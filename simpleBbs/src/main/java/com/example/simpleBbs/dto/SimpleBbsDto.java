package com.example.simpleBbs.dto;

import com.example.simpleBbs.entity.SimpleBbs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleBbsDto {

	private Long id;
	private String title;
	private String content;
	private String writer;

	public SimpleBbsDto(SimpleBbs simpleBbs) {
		this.id = simpleBbs.getId();
		this.title = simpleBbs.getTitle();
		this.content = simpleBbs.getContent();
		this.writer = simpleBbs.getWriter();
	}
}
