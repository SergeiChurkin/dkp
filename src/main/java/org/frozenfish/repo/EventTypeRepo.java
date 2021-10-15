package org.frozenfish.repo;

import org.frozenfish.entity.EventType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@Repository
@CrossOrigin("http://62.109.19.114/")
//@CrossOrigin("http://localhost/")
public interface EventTypeRepo extends CrudRepository<EventType, Long> {
    EventType findById(long id);
    Set<EventType> findAll();
}
