package org.frozenfish.controller;

import org.frozenfish.entity.Event;
import org.frozenfish.entity.EventType;
import org.frozenfish.entity.Screenshot;
import org.frozenfish.entity.User;
import org.frozenfish.repo.EventRepo;
import org.frozenfish.repo.EventTypeRepo;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Controller
public class TableController {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private EventTypeRepo eventTypeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ScreenshotRepo screenshotRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/table/score")
    public String showScoreTable(
                             Model model) {
        model.addAttribute("eventTypes", eventTypeRepo.findAll());
        model.addAttribute("users",userRepo.findAll());
        return "scoreTable";
    }




}
