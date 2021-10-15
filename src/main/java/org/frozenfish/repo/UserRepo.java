package org.frozenfish.repo;

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
public interface UserRepo extends CrudRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);
    Set<User> findAll();
}
