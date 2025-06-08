package com.application.weshorten;

import com.application.weshorten.rest.controllers.URLController;
import com.application.weshorten.rest.dtos.URLDTO;
import com.application.weshorten.rest.services.URLService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(URLController.class)
class URLControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private URLService urlService;

	@Test
	void shortenURL_shouldReturnCreated() throws Exception {
		String json = "{\"url\":\"https://example.com\"}";
		Mockito.when(urlService.shortenURL(Mockito.any())).thenReturn(
				new com.application.weshorten.rest.domain.URLModel(new URLDTO("https://example.com"))
		);

		mockMvc.perform(post("/api/shorten")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isCreated());
	}

	@Test
	void getAllURLs_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/api/links"))
				.andExpect(status().isOk());
	}

	@Test
	void deleteURL_shouldReturnOkOrNotFound() throws Exception {
		Mockito.when(urlService.deleteURL("abc123"))
				.thenReturn(new org.springframework.http.ResponseEntity<>(null, org.springframework.http.HttpStatus.OK));
		mockMvc.perform(delete("/api/links/abc123"))
				.andExpect(status().isOk());
	}
}
