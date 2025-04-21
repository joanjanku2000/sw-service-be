package org.swisscom.serviceapp.infrastructure.service;

import org.springframework.data.domain.PageRequest;
import org.swisscom.serviceapp.domain.model.BaseDocument;
import org.swisscom.serviceapp.infrastructure.dto.BaseDto;
import org.swisscom.serviceapp.infrastructure.dto.RestPage;

import java.util.UUID;

public interface BaseDocumentService<D extends BaseDocument, T extends BaseDto> {
    T save(T dto);

    T update(UUID id, T dto);

    T findById(UUID id);

    RestPage<T> findAll(PageRequest pageRequest);

    void delete(UUID id);
}
