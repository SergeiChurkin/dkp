package org.frozenfish.repo;

import org.frozenfish.entity.Event;
import org.frozenfish.entity.EventType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.Set;


@Repository
@Transactional
@CrossOrigin("http://62.109.19.114/")
//@CrossOrigin("http://localhost/")
public interface EventRepo extends CrudRepository<Event, Long> {

    Event findById(long id);
    Event findByUUID(String uuid);
    Set<Event> findByUsersId(long id);
    Set<Event> findAll();
    Set<Event> findByActiveTrue();
    Set<Event> findByEventType(EventType eventType);
}
