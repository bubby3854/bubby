package com.example.simpleBbs.entity;

import com.example.simpleBbs.dto.SimpleBbsDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SimpleBbs")
@Entity
@Builder
public class SimpleBbs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(length = 1000, nullable = false)
	private String content;

	private String writer;

	public SimpleBbs(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public static SimpleBbs toEntity(SimpleBbsDto dto) {
		SimpleBbs simpleBbs = new SimpleBbs();
		simpleBbs.setTitle(dto.getTitle());
		simpleBbs.setContent(dto.getContent());
		simpleBbs.setWriter(dto.getWriter());
		return simpleBbs;
	}
}
