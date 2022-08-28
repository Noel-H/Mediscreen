package com.noelh.mediscreen.controller;

import com.noelh.mediscreen.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("")
    public String getPatient(Model model){
        log.info("GET /patient");
        model.addAttribute("patientList", patientService.getPatientList());
        return "Patient";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id){
        patientService.deletePatient(id);
        return "redirect:/patient";
    }
}
