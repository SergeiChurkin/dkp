package org.frozenfish.entity;


import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="equip")
public class Equip{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="grade")
    private int grade;

    @Column(name = "cost")
    private double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    private EquipType equipType;

    @ManyToMany(mappedBy = "equip", fetch = FetchType.LAZY)
    private List<User> user;

    public Equip() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public EquipType getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipType equipType) {
        this.equipType = equipType;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Objects.hash( grade, cost );
    }

    @Override
    public boolean equals(Object obj) {
        if ( this ==obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }
        Equip equip = (Equip) obj;
        return Objects.equals( grade, equip.grade ) &&
                Objects.equals( cost, equip.cost ) ;
    }


}
