package org.swisscom.serviceapp.domain.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.swisscom.serviceapp.domain.model.Resource;

import java.util.UUID;

public interface ResourceRepository extends MongoRepository<Resource, UUID> {
}
