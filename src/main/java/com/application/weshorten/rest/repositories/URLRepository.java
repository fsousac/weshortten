package com.application.weshorten.rest.repositories;

import com.application.weshorten.rest.domain.URLModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URLModel, String> {
	URLModel findByShortCode(String shortCode);

	Optional<URLModel> findByOriginalUrl(String original_url);

}
