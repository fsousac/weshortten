package com.application.weshorten.rest.services;

import com.application.weshorten.rest.domain.AccessLogModel;
import com.application.weshorten.rest.domain.URLModel;
import com.application.weshorten.rest.dtos.MessageDTO;
import com.application.weshorten.rest.dtos.URLDTO;
import com.application.weshorten.rest.dtos.URLShowResponseDTO;
import com.application.weshorten.rest.exceptions.URLException;
import com.application.weshorten.rest.exceptions.URLNotFoundException;
import com.application.weshorten.rest.repositories.AccessLogRepository;
import com.application.weshorten.rest.repositories.URLRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class URLService {
	private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int SHORTCODE_LENGTH = 6;
	private final SecureRandom random = new SecureRandom();

	private final URLRepository urlRepository;
	private final AccessLogRepository accessLogRepository;

	@Autowired
	public URLService(URLRepository urlRepository, AccessLogRepository accessLogRepository) {
		this.urlRepository = urlRepository;
		this.accessLogRepository = accessLogRepository;
	}

	private String generateShortCode() {
		StringBuilder sb = new StringBuilder(SHORTCODE_LENGTH);
		for (int i = 0; i < SHORTCODE_LENGTH; i++) {
			sb.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
		}
		return sb.toString();
	}

	public void validateShorten(URLModel received) {
		if (!received.getOriginalUrl().startsWith("http://") && !received.getOriginalUrl().startsWith("https://")) {
			throw new URLException("Invalid URL");
		}
		String bruteOriginal = received.getOriginalUrl().substring(received.getOriginalUrl().indexOf("://") + 3);
		if (bruteOriginal.length() > 500) {
			throw new URLException("URL too long");
		}
		if (urlRepository.findByOriginalUrl("http://" + bruteOriginal).isPresent() || urlRepository.findByOriginalUrl("https://" + bruteOriginal).isPresent()) {
			throw new URLException("URL already shortened");
		}
	}

	public URLModel shortenURL(URLDTO url) {
		URLModel newURL = new URLModel(url);
		this.validateShorten(newURL);
		String tempCode;
		do {
			tempCode = generateShortCode();
		} while (urlRepository.findByShortCode(tempCode) != null);
		newURL.setShortCode(tempCode);
		this.urlRepository.save(newURL);
		return newURL;
	}

	public URLModel findByOriginalUrl(String original_url) throws Exception {
		return this.urlRepository.findByOriginalUrl(original_url).orElseThrow(() -> new Exception("URL not found"));
	}

	public void redirect(String shortCode, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		URLModel model = urlRepository.findByShortCode(shortCode);
		if (model == null) {
			throw new URLNotFoundException("URL not found");
		}
		model.hit();
		urlRepository.save(model);

		String ip = httpRequest.getRemoteAddr();
		if (ip == null || ip.isEmpty()) {
			ip = "unknown";
		}
		String userAgent = httpRequest.getHeader("User-Agent");
		Instant timestamp = Instant.now();
		AccessLogModel log = new AccessLogModel();

		log.setShortCode(shortCode);
		log.setIp(ip);
		log.setUserAgent(userAgent != null ? userAgent : "");
		log.setAccessTime(timestamp);
		accessLogRepository.save(log);

		httpResponse.setStatus(HttpServletResponse.SC_FOUND);
		httpResponse.setHeader("Location", model.getOriginalUrl());
	}

	public ResponseEntity<List<URLShowResponseDTO>> getAllURLs() {
		List<URLShowResponseDTO> urls;
		List<URLModel> urlModels = this.urlRepository.findAll();
		urls = urlModels.stream().sorted(Comparator.comparing(URLModel::getId)).map(url -> new URLShowResponseDTO("https://short.local/" + url.getShortCode(), url.getOriginalUrl(), url.getClicks())).toList();
		return new ResponseEntity<>(urls, HttpStatus.OK);
	}

	public ResponseEntity<MessageDTO> deleteURL(String shortCode) {
		if (this.urlRepository.findByShortCode(shortCode) != null) {
			URLModel url = this.urlRepository.findByShortCode(shortCode);
			this.urlRepository.delete(url);
			return new ResponseEntity<>(new MessageDTO("Link deleted successfully"), HttpStatus.OK);
		} else {
			throw new URLNotFoundException("URL not found");
		}
	}

	public void save(URLModel model) {
		this.urlRepository.save(model);
	}

	public URLModel findByShortCode(String shortCode) {
		return this.urlRepository.findByShortCode(shortCode);
	}
}
