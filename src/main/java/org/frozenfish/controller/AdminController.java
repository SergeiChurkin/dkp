package org.frozenfish.controller;

import org.frozenfish.entity.*;
import org.frozenfish.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private EventTypeRepo eventTypeRepo;
    @Autowired
    private ScreenshotRepo screenshotRepo;
    @Autowired
    private EquipRepo equipRepo;


    @GetMapping
    public String adminIndex() {
        return "admin";
    }


    @GetMapping("users")
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "userlist";
    }

    @PostMapping("minus_points")
    public String minusUserPoints(@RequestParam long user_id,
            @RequestParam int points){
        User theUser = userRepo.findById(user_id);
        theUser.setPoints(theUser.getPoints() - points);
        userRepo.save(theUser);
        return "redirect:/admin/users";
    }

    @PostMapping("plus_points")
    public String plusUserPoints(@RequestParam long user_id,
                                  @RequestParam int points){
        User theUser = userRepo.findById(user_id);
        theUser.setPoints(theUser.getPoints() + points);
        userRepo.save(theUser);
        return "redirect:/admin/users";
    }

    @GetMapping("approve_user/{user}")
    public String approveUserReg(
            @PathVariable User user) {
        User theUser = userRepo.findByUsername(user.getUsername());
        if (theUser!=null) {
            theUser.setConfirm(true);
            theUser.setValidate(true);
            theUser.setRequested(false);
            userRepo.save(theUser);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("off_user/{user}")
    public String offUser(
            @PathVariable User user) {
        User theUser = userRepo.findByUsername(user.getUsername());
        if (theUser!=null) {
            theUser.setActive(false);
            userRepo.save(theUser);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("on_user/{user}")
    public String onUser(
            @PathVariable User user) {
        User theUser = userRepo.findByUsername(user.getUsername());
        if (theUser!=null) {
            theUser.setActive(true);
            userRepo.save(theUser);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("delete_user/{user}")
    public String deleteUser(
            @PathVariable User user) {
        User theUser = userRepo.findByUsername(user.getUsername());
        if (theUser!=null && !theUser.getRoles().contains("ADMIN")) {
            Set<Event> events = eventRepo.findAll();
            for(Event e:events){
                e.getUsers().remove(theUser);
                e.getApprovedUsers().remove(theUser);
            }
            eventRepo.saveAll(events);
            List<Equip> equips = equipRepo.findAll();
            for (Equip equip:equips){
                equip.getUser().remove(theUser);
            }
            equipRepo.saveAll(equips);
            Set<Screenshot> screenshots = screenshotRepo.findAll();
            for(Screenshot s:screenshots){
                s.getUsers().remove(theUser);
            }
            screenshotRepo.saveAll(screenshots);

            userRepo.delete(theUser);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("approve_user_to_event/{event_id}/{user_id}")
    public String approveUserToEvent(
            @PathVariable long event_id,
            @PathVariable long user_id) {
        Event theEvent = eventRepo.findById(event_id);
        User theUser = userRepo.findById(user_id);
        if(theUser!=null && theEvent!=null && theUser.isActive()) {
            long typeId = theEvent.getEventType().getId();
            EventType theEventType = eventTypeRepo.findById(typeId);
            theUser.addApprovedEvent(theEvent);
            theUser.setPoints(theUser.getPoints() + theUser.getCoefficient() * theEventType.getScore() * theEvent.getCost());
            userRepo.save(theUser);
        }
        return "redirect:/admin/events/"+theEvent.getId();
    }

    @GetMapping("events")
    public String eventList(Model model) {
        model.addAttribute("events", eventRepo.findAll());
        model.addAttribute("event_types", eventTypeRepo.findAll());
        return "events";
    }

    @GetMapping("event_types")
    public String eventTypeList(Model model) {
        model.addAttribute("eventTypes", eventTypeRepo.findAll());
        return "eventtypelist";
    }
    @GetMapping("event_type_del/{id}")
    public String eventTypeDel(@PathVariable long id) {
        EventType thEventType = eventTypeRepo.findById(id);
        if(thEventType!=null)
        {
            List<Event> events = thEventType.getEvents();
            for(Event e:events){
                e.setEventType(null);
                eventRepo.save(e);
            }

            eventTypeRepo.delete(thEventType);
        }
        return "redirect:/admin/event_types/";
    }

    @GetMapping("event_del/{event_id}")
    public String eventDel(@PathVariable long event_id) {
        Event thEvent = eventRepo.findById(event_id);
        if(thEvent!=null) {
            Set<User> usersWithThisEvent = thEvent.getUsers();
            for (User u : usersWithThisEvent) {
                u.removeEvent(thEvent);
            }
            userRepo.saveAll(usersWithThisEvent);
            eventRepo.delete(thEvent);
        }
        return "redirect:/admin/events/";
    }

    @GetMapping("events/{event_id}")
    public String eventInfo(@PathVariable long event_id,
                            Model model) {
        Event theEvent = eventRepo.findById(event_id);
        if(!theEvent.isActive() && theEvent!=null) {
            Set<User> userSet = theEvent.getUsers();
            Set<User> userApproved = theEvent.getApprovedUsers();
            Map<User, String> userWithScreen = new HashMap<>();
            Map<User,String> userApprovedModel = new HashMap<>();
            List<User> userWithoutScreen = new ArrayList<>();
            for (User u : userSet) {
                if(u.isActive()) {
                    Screenshot theScreenshot = screenshotRepo.findByUsersAndEvents(u, theEvent);
                    if (theScreenshot != null) {
                        if (!userApproved.contains(u)) {
                            userWithScreen.put(u, theScreenshot.getFilename());
                        } else {
                            userApprovedModel.put(u, theScreenshot.getFilename());
                        }
                    } else {
                        userWithoutScreen.add(u);
                    }
                }
            }
            model.addAttribute("users_approve_event",userApprovedModel);
            model.addAttribute("users_confirm_event",userWithScreen);
            model.addAttribute("users_not_confirm_event",userWithoutScreen);
        }
        model.addAttribute("event",theEvent);
        return "eventinfo";
    }

    @PostMapping("event_add")
    public String eventAdd(
            @RequestParam long eventTypeId,
            @RequestParam float newCost) {
        EventType theEventType = eventTypeRepo.findById(eventTypeId);
        if(theEventType!=null) {
            Event theEvent = new Event();
            theEvent.setCost(newCost);
            theEvent.setEventType(theEventType);
            theEvent.setUUID(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            theEvent.setActive(true);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
            theEvent.setDateCreated(now.format(formatter));
            eventRepo.save(theEvent);
        }
        return "redirect:/admin/events";
    }



    @PostMapping("event_save")
    public String eventSaveChanges(
            @RequestParam long event_id,
            @RequestParam float new_cost) {
        Event theEvent = eventRepo.findById(event_id);
        if(theEvent!=null) {
            theEvent.setCost(new_cost);
            eventRepo.save(theEvent);
        }
        return "redirect:/admin/events";
    }

    @PostMapping("event_type_save")
    public String eventTypeSaveChanges(
            @RequestParam long eventTypeId,
            @RequestParam int score) {
        EventType theEventType = eventTypeRepo.findById(eventTypeId);
        if(theEventType!=null) {
            theEventType.setScore(score);
            eventTypeRepo.save(theEventType);
        }
        return "redirect:/admin/event_types";
    }

    @PostMapping("event_close")
    public String eventClose(
            @RequestParam long event_id,
            @RequestParam int closeTime) {
        Event theEvent = eventRepo.findById(event_id);
        if(theEvent!=null) {
            theEvent.setActive(false);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime modifiedDate = now.plusMinutes(closeTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
            theEvent.setDateClosed(modifiedDate.format(formatter));
            eventRepo.save(theEvent);
        }
        return "redirect:/admin/events";
    }

    @PostMapping("event_type_add")
    public String eventTypeAdd(
            @RequestParam String eventTypeName,
            @RequestParam int eventTypeScore) {
        EventType eventType = new EventType();
        eventType.setName(eventTypeName);
        eventType.setScore(eventTypeScore);
        eventTypeRepo.save(eventType);
        return "redirect:/admin/eventtypes";
    }



}
