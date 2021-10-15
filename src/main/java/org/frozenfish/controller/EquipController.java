package org.frozenfish.controller;

import org.frozenfish.entity.Equip;
import org.frozenfish.entity.EquipType;
import org.frozenfish.entity.Level;
import org.frozenfish.entity.Prof;
import org.frozenfish.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class EquipController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EquipRepo equipRepo;
    @Autowired
    private LevelRepo levelRepo;
    @Autowired
    private ProfRepo profRepo;
    @Autowired
    private EquipTypeRepo equipTypeRepo;

    @GetMapping("equips")
    public String equipList(Model model) {
        model.addAttribute("equips", equipRepo.findAll());
        model.addAttribute("levels", levelRepo.findAll());
        model.addAttribute("profs", profRepo.findAll());
        model.addAttribute("equip_types", equipTypeRepo.findAll());
        return "equiplist";
    }

    @PostMapping("equip_add")
    public String equipAdd(
            @RequestParam long equipTypeId,
            @RequestParam int grade,
            @RequestParam float cost,
            Model model
    ){
        EquipType theEquipType = equipTypeRepo.findById(equipTypeId);
        Equip theEquip = new Equip();
        theEquip.setCost(cost);
        theEquip.setGrade(grade);
        theEquip.setEquipType(theEquipType);
        equipRepo.save(theEquip);
        return "redirect:/admin/equips";
    }

    @PostMapping("equip_type_add")
    public String equipTypeAdd(@RequestParam String equipTypeName,
                               Model model) {
        EquipType equipType = new EquipType();
        equipType.setName(equipTypeName);
        equipTypeRepo.save(equipType);
        return "redirect:/admin/equips";
    }

    @PostMapping("equip_save")
    public String equipSaveChanges(@RequestParam long equip_id,
                                   @RequestParam float new_cost,
                                   Model model) {
        Equip theEquip = equipRepo.findById(equip_id);
        theEquip.setCost(new_cost);
        equipRepo.save(theEquip);
        return "redirect:/admin/equips";
    }

    @PostMapping("level_save")
    public String levelSaveChanges(@RequestParam long level_id,
                                   @RequestParam float new_cost,
                                   Model model) {
        Level theLevel = levelRepo.findById(level_id);
        theLevel.setCost(new_cost);
        levelRepo.save(theLevel);
        return "redirect:/admin/equips";
    }

    @PostMapping("level_add")
    public String levelAdd(@RequestParam int levelValue,
                           @RequestParam float new_cost,
                           Model model) {
        Level theLevel = new Level();
        theLevel.setValue(levelValue);
        theLevel.setCost(new_cost);
        levelRepo.save(theLevel);
        return "redirect:/admin/equips";
    }

    @PostMapping("prof_save")
    public String profSaveChanges(@RequestParam long prof_id,
                                  @RequestParam float new_cost,
                                  Model model) {
        Prof thProf = profRepo.findById(prof_id);
        thProf.setCost(new_cost);
        profRepo.save(thProf);
        return "redirect:/admin/equips";
    }

    @PostMapping("prof_add")
    public String profAdd(@RequestParam String profValue,
                          @RequestParam float new_cost,
                          Model model) {
        Prof thProf = new Prof();
        thProf.setValue(profValue);
        thProf.setCost(new_cost);
        profRepo.save(thProf);
        return "redirect:/admin/equips";
    }

}
