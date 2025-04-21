package org.swisscom.serviceapp.infrastructure.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.swisscom.serviceapp.domain.model.Owner;
import org.swisscom.serviceapp.domain.repo.OwnerRepository;
import org.swisscom.serviceapp.infrastructure.api.exception.ExceptionMessage;
import org.swisscom.serviceapp.infrastructure.api.exception.NotFoundException;
import org.swisscom.serviceapp.infrastructure.dto.OwnerDto;
import org.swisscom.serviceapp.infrastructure.dto.RestPage;
import org.swisscom.serviceapp.infrastructure.mapper.OwnerMapper;
import org.swisscom.serviceapp.infrastructure.service.OwnerService;

import java.util.ConcurrentModificationException;
import java.util.UUID;

import static org.swisscom.serviceapp.infrastructure.api.exception.ExceptionMessage.CONCURRENT_MODIFICATION;
import static org.swisscom.serviceapp.infrastructure.service.impl.AppServiceServiceImpl.VERSION;

@Service
public class OwnerServiceImpl implements OwnerService {
    // cache primarily for find-all
    private static final String CACHE_OWNER = "owner";

    // cache primarily for find-by-id - individual documents
    private static final String CACHE_OWNERS = "owners";
    private static final String SERVICE_ENTITY_NAME = "Owner";
    private static final String ID = "_id";
    final Logger log = LoggerFactory.getLogger(OwnerServiceImpl.class);
    private final OwnerRepository ownerRepository;
    private final MongoTemplate mongoTemplate;

    public OwnerServiceImpl(OwnerRepository ownerRepository, MongoTemplate mongoTemplate) {
        this.ownerRepository = ownerRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Caching(
            evict = @CacheEvict(value = CACHE_OWNERS, allEntries = true),
            put = @CachePut(cacheNames = CACHE_OWNER, key = "#result.id()")
    )
    @Override
    public OwnerDto save(final OwnerDto ownerDto) {
        return OwnerMapper.toDTO(ownerRepository.save(OwnerMapper.toEntity(ownerDto)));
    }

    /**
     * Implements a manual optimistic locking mechanism leveraging
     * mongotemplate
     */
    @Caching(
            put = @CachePut(cacheNames = CACHE_OWNER, key = "#id"),
            evict = @CacheEvict(value = CACHE_OWNERS, allEntries = true),
            cacheable = @Cacheable(value = CACHE_OWNER, key = "#id")
    )
    @Override
    public OwnerDto update(final UUID id, final OwnerDto ownerDto) {

        ensureServiceExists(id);

        Query query = new Query(
                Criteria.where(ID).is(id).and(VERSION).is(ownerDto.version())
        );
        Update update = new Update()
                .set(VERSION, ownerDto.version() + 1)
                .set("name", ownerDto.name())
                .set("accountNumber", ownerDto.accountNumber())
                .set("level", ownerDto.level());

        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions()
                .returnNew(true);

        Owner result = mongoTemplate.findAndModify(query, update, findAndModifyOptions, Owner.class);

        if (result == null) {
            throw new ConcurrentModificationException(CONCURRENT_MODIFICATION.getMessage());
        }

        return OwnerMapper.toDTO(result);
    }

    @Cacheable(value = CACHE_OWNER, key = "#id")
    @Override
    public OwnerDto findById(final UUID id) {
        log.debug("Searching for service with id {}", id);
        return OwnerMapper.toDTO(ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id))));
    }

    @Cacheable(CACHE_OWNERS)
    @Override
    public RestPage<OwnerDto> findAll(PageRequest pageRequest) {
        return new RestPage<>(ownerRepository.findAll(pageRequest).map(OwnerMapper::toDTO));
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_OWNERS, allEntries = true),
            @CacheEvict(value = CACHE_OWNER, key = "#id")
    })
    @Override
    public void delete(UUID id) {
        ensureServiceExists(id);
        ownerRepository.deleteById(id);
    }

    private void ensureServiceExists(UUID id) {
        if (!ownerRepository.existsById(id)) {
            throw new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id));
        }
    }
}
