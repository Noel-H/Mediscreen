package com.noelh.mediscreen.controller;

import com.noelh.mediscreen.dto.PatientDTO;
import com.noelh.mediscreen.model.Patient;
import com.noelh.mediscreen.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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
        log.info("GET /patient/add");
        model.addAttribute("patientDTO", new PatientDTO());
        return "patient/AddPatient";
    }

    @PostMapping("/add")
    public String postAddPatient(@ModelAttribute PatientDTO patientDTO){
        log.info("POST /patient/add");
        patientService.addPatient(patientDTO);
        return "redirect:/patient";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePatient(@PathVariable("id") Long id,Model model){
        log.info("GET /patient/update/{}", id);
        Patient patient;
        try {
            patient = patientService.getPatientById(id);
        } catch (EntityNotFoundException e) {
            log.error("GET /patient/update/{} : ERROR = {}", id, e.getMessage());
            return "redirect:/patient";
        }
        model.addAttribute("patient", patient);
        return "patient/UpdatePatient";
    }

    @PostMapping("/update/{id}")
    public String postUpdatePatient(@PathVariable("id") Long id, @ModelAttribute Patient patient){
        log.info("POST /patient/update/{}", id);
        patientService.updatePatient(id, new PatientDTO(patient.getLastName(), patient.getFirstName()));
        return "redirect:/patient";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id){
        log.info("GET /patient/delete/{}", id);
        try {
            patientService.deletePatient(id);
        } catch (EntityNotFoundException e) {
            log.error("GET /patient/delete/{} : ERROR = {}", id, e.getMessage());
            return "redirect:/patient";
        }
        return "redirect:/patient";
    }
}
