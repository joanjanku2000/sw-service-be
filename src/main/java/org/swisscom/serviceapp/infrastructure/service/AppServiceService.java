package org.swisscom.serviceapp.infrastructure.service;

import org.springframework.data.domain.PageRequest;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;
import org.swisscom.serviceapp.infrastructure.dto.RestPage;

import java.util.UUID;

/**
 * Service interface providing
 * basic CRUD operations for {@link AppService}
 *
 * Extracted into an interface so Liskov's Substitution Principle can
 * be implemented
 */
public interface AppServiceService extends BaseDocumentService<AppService,AppServiceDto> {

}
