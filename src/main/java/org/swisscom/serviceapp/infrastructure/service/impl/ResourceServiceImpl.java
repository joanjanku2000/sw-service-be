package org.swisscom.serviceapp.infrastructure.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.swisscom.serviceapp.infrastructure.dto.ResourceDto;
import org.swisscom.serviceapp.infrastructure.dto.RestPage;
import org.swisscom.serviceapp.infrastructure.service.ResourceService;

import java.util.UUID;

@Component
public class ResourceServiceImpl implements ResourceService {
    @Override
    public ResourceDto save(ResourceDto dto) {
        return null;
    }

    @Override
    public ResourceDto update(UUID id, ResourceDto dto) {
        return null;
    }

    @Override
    public ResourceDto findById(UUID id) {
        return null;
    }

    @Override
    public RestPage<ResourceDto> findAll(PageRequest pageRequest) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
