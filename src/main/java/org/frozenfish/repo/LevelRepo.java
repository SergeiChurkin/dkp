package org.frozenfish.repo;

import org.frozenfish.entity.Level;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://62.109.19.114/")
//@CrossOrigin("http://localhost/")
public interface LevelRepo extends CrudRepository<Level, Long> {
    Level findById(long id);
    Level findByValue(int value);
}
