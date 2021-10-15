package org.frozenfish.repo;

import org.frozenfish.entity.Equip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
@CrossOrigin("http://62.109.19.114/")
//@CrossOrigin("http://localhost/")
public interface EquipRepo extends CrudRepository<Equip, Long> {

    List<Equip> findAll();
    Equip findById(long id);
}
