package com.application.weshorten.rest.repositories;

import com.application.weshorten.rest.domain.AccessLogModel;
import com.application.weshorten.rest.domain.URLModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccessLogRepository extends JpaRepository<AccessLogModel, UUID> {

}
