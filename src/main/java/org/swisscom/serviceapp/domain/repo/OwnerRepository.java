package org.swisscom.serviceapp.domain.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.swisscom.serviceapp.domain.model.Owner;
import org.swisscom.serviceapp.domain.model.Resource;

import java.util.UUID;

public interface OwnerRepository extends MongoRepository<Owner, UUID> {
}
