package org.frozenfish.repo;


import org.frozenfish.entity.Prof;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://62.109.19.114/")
//@CrossOrigin("http://localhost/")
public interface ProfRepo extends CrudRepository<Prof, Long> {
    Prof findById(long id);
    Prof findByValue(String value);
}
