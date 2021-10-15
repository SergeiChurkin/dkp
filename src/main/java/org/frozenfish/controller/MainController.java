package org.frozenfish.controller;


import org.frozenfish.entity.Equip;
import org.frozenfish.entity.User;
import org.frozenfish.repo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LevelRepo levelRepo;

    @Autowired
    private ProfRepo profRepo;

    @Autowired
    private EquipRepo equipRepo;

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User user,
            Model model) {
        model.addAttribute("equips", equipRepo.findAll());
        model.addAttribute("levels", levelRepo.findAll());
        model.addAttribute("profs", profRepo.findAll());
        model.addAttribute("user", user);
        return "main";
    }
    @GetMapping("/main/edit")
    public String edit(
            @AuthenticationPrincipal User user,
            Model model) {
        User theUser = userRepo.findByUsername(user.getUsername());
        model.addAttribute("user", theUser);
        model.addAttribute("equips", equipRepo.findAll());
        model.addAttribute("levels", levelRepo.findAll());
        model.addAttribute("profs", profRepo.findAll());
        model.addAttribute("user_equips",theUser.getEquip());
        model.addAttribute("user_level",theUser.getLevel());
        model.addAttribute("user_prof",theUser.getProf());
        return "main_edit";
    }

    @GetMapping("/lk")
    public String lk(
            @AuthenticationPrincipal User user,
            Model model) {
        User theUser = userRepo.findByUsername(user.getUsername());
        model.addAttribute("user", theUser);
        model.addAttribute("equips",theUser.getEquip());
        model.addAttribute("level",theUser.getLevel());
        model.addAttribute("prof",theUser.getProf());
        return "lk";
    }

    @PostMapping("/main")
    @Transactional
    public String fillUserProfile(@AuthenticationPrincipal User user,
                                  @RequestParam int level,
                                  @RequestParam String prof,
                                  @RequestParam Map<String,String> mapRequest,
                                  @RequestParam("reg_screenshot") MultipartFile regFile,
                                  Model model) throws IOException {
        User theUser = userRepo.findByUsername(user.getUsername());
        double coefficient=0.00;
        if(regFile!=null){
            File uploadDir = new File(uploadPath+"/"+theUser.getId());
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile +"-"+regFile.getOriginalFilename();

            regFile.transferTo(new File(uploadPath+"/"+theUser.getId()+"/"+resultFilename));

            theUser.setRegScreenshot(resultFilename);
        }


        for (Map.Entry<String, String> entry : mapRequest.entrySet()) {
            if(entry.getKey().contains("type_")){
                Equip theEquip = equipRepo.findById(Integer.parseInt(entry.getValue()));
                coefficient += theEquip.getCost();
                theUser.addEquipItem(theEquip);
            }
        }
        coefficient += levelRepo.findByValue(level).getCost();
        coefficient += profRepo.findByValue(prof).getCost();
        theUser.setLevel(levelRepo.findByValue(level));
        theUser.setProf(profRepo.findByValue(prof));
        theUser.setRequested(true);
        theUser.setCoefficient(coefficient);
        userRepo.save(theUser);
        return "redirect:/logout";
    }

    @PostMapping("/main/update_equip")
    @Transactional
    public String updateUserProfile(@AuthenticationPrincipal User user,
                                  @RequestParam int level,
                                  @RequestParam String prof,
                                  @RequestParam Map<String,String> mapRequest,
                                  @RequestParam("reg_screenshot") MultipartFile regFile,
                                  Model model) throws IOException {
        User theUser = userRepo.findByUsername(user.getUsername());
        double coefficient=0.00;
        if(regFile!=null){
            File uploadDir = new File(uploadPath+"/"+theUser.getId());
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile +"-"+regFile.getOriginalFilename();

            regFile.transferTo(new File(uploadPath+"/"+theUser.getId()+"/"+resultFilename));

            theUser.setRegScreenshot(resultFilename);
        }

        theUser.clearEquips();
        for (Map.Entry<String, String> entry : mapRequest.entrySet()) {
            if(entry.getKey().contains("type_")){
                Equip theEquip = equipRepo.findById(Integer.parseInt(entry.getValue()));
                theUser.addEquipItem(theEquip);
                coefficient += theEquip.getCost();
            }
        }
        theUser.setLevel(levelRepo.findByValue(level));
        theUser.setProf(profRepo.findByValue(prof));
        coefficient += levelRepo.findByValue(level).getCost();
        coefficient += profRepo.findByValue(prof).getCost();
        theUser.setCoefficient(coefficient);
        theUser.setRequested(true);
        theUser.setValidate(false);
        theUser.setConfirm(false);
        userRepo.save(theUser);
        return "redirect:/logout";
    }


}
