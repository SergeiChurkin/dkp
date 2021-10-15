package org.frozenfish.repo;


import org.frozenfish.entity.EquipType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://62.109.19.114/")
//@CrossOrigin("http://localhost/")
public interface EquipTypeRepo extends CrudRepository<EquipType, Long> {
    EquipType findById(long id);
}
