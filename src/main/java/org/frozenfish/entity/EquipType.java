package org.frozenfish.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="equip_type")
public class EquipType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "equipType")
    private List<Equip> equips;

    public EquipType() {
    }

    public EquipType(String name) {
        this.name = name;

    }

    public List<Equip> getEquips() {
        return equips;
    }

    public void setEquips(List<Equip> equips) {
        this.equips = equips;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
