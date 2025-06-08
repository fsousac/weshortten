package com.application.weshorten.rest.domain;

import com.application.weshorten.rest.dtos.URLDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "url")
@Table(name = "url")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "shortCode")

public class URLModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(name = "shortCode", unique = true, updatable = false, nullable = false, length = 6, columnDefinition = "CHAR(6)")
	private String shortCode;
	@Column(unique = true, length = 500)
	private String originalUrl;
	private int clicks = 0;

	public URLModel(URLDTO urlDTO) {
		this.originalUrl = urlDTO.url();
	}

	public void hit() {
		this.clicks++;
	}
}
