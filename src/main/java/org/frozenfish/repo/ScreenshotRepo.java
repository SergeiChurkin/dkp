package org.frozenfish.repo;

import org.frozenfish.entity.Event;
import org.frozenfish.entity.Screenshot;
import org.frozenfish.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
@CrossOrigin("http://62.109.19.114/")
//@CrossOrigin("http://localhost/")
public interface ScreenshotRepo extends CrudRepository<Screenshot,Long> {
    Screenshot findByUsersAndEvents(User user, Event event);
    Set<Screenshot> findAll();
}
