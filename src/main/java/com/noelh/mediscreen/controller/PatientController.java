package com.noelh.mediscreen.controller;

import com.noelh.mediscreen.dto.PatientDTO;
import com.noelh.mediscreen.model.Patient;
import com.noelh.mediscreen.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "patient/Patient";
    }

    @GetMapping("/add")
    public String getAddPatient(Model model){
        model.addAttribute("patientDTO", new PatientDTO());
        return "patient/AddPatient";
    }

    @PostMapping("/add")
    public String postAddPatient(@ModelAttribute PatientDTO patientDTO){
        patientService.addPatient(patientDTO);
        return "redirect:/patient";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePatient(@PathVariable("id") Long id,Model model){
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patient/UpdatePatient";
    }

    @PostMapping("/update")
    public String postUpdatePatient(@ModelAttribute Patient patient){
        patientService.updatePatient(patient.getId(), new PatientDTO(patient.getLastName(), patient.getFirstName()));
        return "redirect:/patient";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id){
        patientService.deletePatient(id);
        return "redirect:/patient";
    }
}
