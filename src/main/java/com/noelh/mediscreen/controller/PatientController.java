package com.noelh.mediscreen.controller;

import com.noelh.mediscreen.dto.PatientDTO;
import com.noelh.mediscreen.model.Patient;
import com.noelh.mediscreen.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

/**
 * Patient Controller
 */
@Slf4j
@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    /**
     * GET request to access /patient endpoint
     * @param model used for the html template
     * @return patient/Patient.html
     */
    @GetMapping("")
    public String getPatient(Model model){
        log.info("GET /patient");
        model.addAttribute("patientList", patientService.getPatientList());
        return "patient/Patient";
    }

    /**
     * GET request to access /patient/add page
     * @param model used for the html template
     * @return patient/AddPatient.html
     */
    @GetMapping("/add")
    public String getAddPatient(Model model){
        log.info("GET /patient/add");
        model.addAttribute("patientDTO", new PatientDTO());
        return "patient/AddPatient";
    }

    /**
     * POST request to add patient at /patient/add endpoint
     * @param patientDTO used for the html template
     * @return a redirection at /patient endpoint
     */
    @PostMapping("/add")
    public String postAddPatient(@ModelAttribute PatientDTO patientDTO){
        log.info("POST /patient/add");
        patientService.addPatient(patientDTO);
        return "redirect:/patient";
    }

    /**
     * GET request to access /update/{id}
     * @param id used for the html template
     * @param model used for the html template
     * @return patient/UpdatePatient.html
     */
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

    /**
     * POST request to update at /update/{id} endpoint
     * @param id used for the html template
     * @param patient used for the html template
     * @return a redirection at /patient endpoint
     */
    @PostMapping("/update/{id}")
    public String postUpdatePatient(@PathVariable("id") Long id, @ModelAttribute Patient patient){
        log.info("POST /patient/update/{}", id);
        patientService.updatePatient(id, new PatientDTO(patient.getLastName(), patient.getFirstName()));
        return "redirect:/patient";
    }

    /**
     * GET request to delete at /delete/{id} endpoint
     * @param id used for the html template
     * @return a redirection at /patient endpoint
     */
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
