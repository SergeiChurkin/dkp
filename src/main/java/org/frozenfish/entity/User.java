package org.frozenfish.entity;

import org.frozenfish.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token")
    private String token;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "active")
    private boolean active;

    @Column(name = "confirm")
    private boolean confirm;

    @Column(name = "validate")
    private boolean validate;

    @Column(name = "requested")
    private boolean requested;

    @ManyToOne(fetch = FetchType.LAZY)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    private Prof prof;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Equip> equip;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Event> approvedEvents = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Screenshot> screenshots = new HashSet<>();

    private String regScreenshot;

    @Column(name = "points")
    private double points = 0.0;

    @Column(name = "coefficient")
    private double coefficient = 0.0;

    public User() {
    }


    public void addScreenshot(Screenshot screenshot){
        screenshots.add(screenshot);
        screenshot.getUsers().add(this);
        }

    public void addEvent(Event event) {
        events.add(event);
        event.getUsers().add(this);
        }

    public void removeEvent(Event event){
        events.remove(event);
        event.getUsers().remove(this);
        }

    public void addApprovedEvent(Event event){
        approvedEvents.add(event);
        event.getUsers().add(this);
    }

    public void removeApprovedEvent(Event event){
        approvedEvents.remove(event);
        event.getApprovedUsers().remove(this);
    }

    public void addEquipItem(Equip item){
        equip.add(item);
        item.getUser().add(this);
    }

    public void clearEquips(){
        equip.clear();
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public boolean isRL() {
        return roles.contains(Role.RL);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Prof getProf() {
        return prof;
    }

    public void setProf(Prof prof) {
        this.prof = prof;
    }

    public List<Equip> getEquip() {
        return equip;
    }

    public void setEquip(List<Equip> equip) {
        this.equip = equip;
    }

    public String getRegScreenshot() {
        return regScreenshot;
    }

    public void setRegScreenshot(String regScreenshot) {
        this.regScreenshot = regScreenshot;
    }



    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(Set<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public Set<Event> getApprovedEvents() {
        return approvedEvents;
    }

    public void setApprovedEvents(Set<Event> approvedEvents) {
        this.approvedEvents = approvedEvents;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

}
