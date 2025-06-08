package com.application.weshorten.rest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "access_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessLogModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 6)
	private String shortCode;

	@Column(nullable = false)
	private String ip;

	@Column(nullable = false)
	private String userAgent;

	@Column(nullable = false)
	private Instant accessTime;

}
