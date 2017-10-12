package org.jacpfx.kube.repository;

import org.jacpfx.kube.entity.Person;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by amo on 04.04.17.
 */

public interface ReactiveUserRepository extends ReactiveCrudRepository<Person, String> {

}
