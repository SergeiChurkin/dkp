package org.frozenfish.controller;

import org.frozenfish.entity.Event;
import org.frozenfish.entity.Screenshot;
import org.frozenfish.entity.User;
import org.frozenfish.repo.EventRepo;
import org.frozenfish.repo.ScreenshotRepo;
import org.frozenfish.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class EventController {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ScreenshotRepo screenshotRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/event")
    public String eventIndex(@AuthenticationPrincipal User user,
                             Model model) {
        User theUser = userRepo.findByUsername(user.getUsername());
        if(theUser!=null && theUser.isActive()) {
            Set<Event> userEvents = eventRepo.findByUsersId(theUser.getId());
            Set<Event> eventsWithoutScreenshot = new HashSet<>();
            Set<Event> eventsWithScreenshot = new HashSet<>();
            Set<Event> eventsNotClosed = new HashSet<>();
            for (Event event : userEvents) {
                if(!event.isActive()) {
                    Screenshot theScreenshot = screenshotRepo.findByUsersAndEvents(theUser, event);
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
                    now.format(formatter);
                    LocalDateTime closeTime = LocalDateTime.parse(event.getDateClosed(),formatter);
                    if (theScreenshot == null) {
                        if(now.isBefore(closeTime)) {
                            if(event.getEventType()!=null) {
                                eventsWithoutScreenshot.add(event);
                            }
                        }
                    }
                    else {
                        if(now.isBefore(closeTime)) {
                            if(event.getEventType()!=null) {
                                eventsWithScreenshot.add(event);
                            }
                        }
                    }
                }else {
                    eventsNotClosed.add(event);
                }
            }
            Set<Event> activeEvents = eventRepo.findByActiveTrue();
            activeEvents.removeAll(userEvents);
            model.addAttribute("eventsWithoutScreenshot", eventsWithoutScreenshot);
            model.addAttribute("eventsWithScreenshot", eventsWithScreenshot);
            model.addAttribute("eventsNotClosed", eventsNotClosed);
            model.addAttribute("activeEvents", activeEvents);
        }
        return "event";
    }



    @PostMapping("/event_take")
    public String takeEvent(@AuthenticationPrincipal User user,
                            @RequestParam long eventId){
        User theUser = userRepo.findByUsername(user.getUsername());
        Event theEvent = eventRepo.findById(eventId);
        if(theUser!=null && theEvent!=null && theUser.isActive()) {
            theUser.addEvent(theEvent);
            userRepo.save(theUser);
            eventRepo.save(theEvent);
        }
        return "redirect:/event";
    }

    @PostMapping("/event/approve")
    public String approveEvent(
            @AuthenticationPrincipal User user,
            @RequestParam long eventId,
            @RequestParam("screenshot") MultipartFile screenshot,
            Model model
    ) throws IOException {
        User theUser = userRepo.findByUsername(user.getUsername());
        Event theEvent = eventRepo.findById(eventId);
        if(theUser!=null && theEvent!=null && theUser.isActive()) {
            Screenshot theScreenshot = new Screenshot();
            if (screenshot != null) {
                File uploadDir = new File(uploadPath + "/" + theEvent.getUUID());
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString().substring(0, 10);
                String resultFilename = uuidFile + "-" + screenshot.getOriginalFilename();
                theScreenshot.setFilename(resultFilename);
                screenshot.transferTo(new File(uploadPath + "/" + theEvent.getUUID() + "/" + resultFilename));
                theScreenshot.setFilename(resultFilename);
            }
            theUser.addScreenshot(theScreenshot);
            theUser.addEvent(theEvent);
            theEvent.addScreenshot(theScreenshot);

            screenshotRepo.save(theScreenshot);
            userRepo.save(theUser);
            eventRepo.save(theEvent);
        }
        return "redirect:/event";
    }


}
