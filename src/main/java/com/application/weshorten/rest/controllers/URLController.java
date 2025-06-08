package com.application.weshorten.rest.controllers;

import com.application.weshorten.rest.domain.URLModel;
import com.application.weshorten.rest.dtos.MessageDTO;
import com.application.weshorten.rest.dtos.URLDTO;
import com.application.weshorten.rest.dtos.URLShortenedResponseDTO;
import com.application.weshorten.rest.dtos.URLShowResponseDTO;
import com.application.weshorten.rest.services.URLService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class URLController {
	private final URLService urlService;

	private final String domain;

	@Autowired
	public URLController(URLService urlService,
						 @Value("${app.shortener.domain}") String domain) {
		this.urlService = urlService;
		this.domain = domain;
	}

	@PostMapping("/api/shorten")
	public ResponseEntity<URLShortenedResponseDTO> shortenURL(@RequestBody URLDTO url) {
		URLModel newURL = urlService.shortenURL(url);
		URLShortenedResponseDTO dto = new URLShortenedResponseDTO(
				"https://short.local/" + newURL.getShortCode(),
				newURL.getOriginalUrl()
		);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping("/api/links")
	public ResponseEntity<List<URLShowResponseDTO>> getAllURLs() {
		return urlService.getAllURLs();
	}

	@DeleteMapping("/api/links/{short_code}")
	public ResponseEntity<MessageDTO> deleteURL(@PathVariable String short_code) {
		return urlService.deleteURL(short_code);
	}

	@GetMapping("/{shortCode}")
	public void redirect(@PathVariable String shortCode,
						 HttpServletRequest httpRequest,
						 HttpServletResponse httpResponse) {
		urlService.redirect(shortCode, httpRequest, httpResponse);
	}
}
