package com.application.weshorten;

import com.application.weshorten.rest.domain.URLModel;
import com.application.weshorten.rest.dtos.URLDTO;
import com.application.weshorten.rest.exceptions.URLException;
import com.application.weshorten.rest.exceptions.URLNotFoundException;
import com.application.weshorten.rest.repositories.URLRepository;
import com.application.weshorten.rest.services.URLService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class URLServiceTest {

	private URLRepository urlRepository;
	private URLService urlService;

	@BeforeEach
	void setUp() {
		urlRepository = mock(URLRepository.class);
		urlService = new URLService(urlRepository);
	}

	@Test
	void shortenURL_shouldCreateShortenedUrl() {
		URLDTO dto = new URLDTO("https://example.com");
		when(urlRepository.findByOriginalUrl(anyString())).thenReturn(Optional.empty());
		when(urlRepository.findByShortCode(anyString())).thenReturn(null);

		URLModel result = urlService.shortenURL(dto);

		assertNotNull(result.getShortCode());
		assertEquals("https://example.com", result.getOriginalUrl());
		verify(urlRepository).save(any(URLModel.class));
	}

	@Test
	void shortenURL_shouldThrowIfUrlInvalid() {
		URLDTO dto = new URLDTO("ftp://example.com");
		assertThrows(URLException.class, () -> urlService.shortenURL(dto));
	}

	@Test
	void redirect_shouldThrowIfNotFound() {
		when(urlRepository.findByShortCode("abc123")).thenReturn(null);
		assertThrows(URLNotFoundException.class, () -> urlService.redirect("abc123", null,null));
	}

	@Test
	void validateShorten_shouldThrowIfAlreadyShortened() {
		URLModel model = new URLModel();
		model.setOriginalUrl("https://example.com");
		when(urlRepository.findByOriginalUrl(anyString())).thenReturn(Optional.of(model));
		assertThrows(URLException.class, () -> urlService.validateShorten(model));
	}
}
