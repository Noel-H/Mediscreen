package com.noelh.mediscreen.service;

import com.noelh.mediscreen.dto.PatientDTO;
import com.noelh.mediscreen.model.Patient;
import com.noelh.mediscreen.repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatientList(){
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id){
        if (!patientRepository.existsById(id)){
            throw new EntityNotFoundException("Id not found : " +id);
        }
        return patientRepository.getReferenceById(id);
    }

    public Patient addPatient(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setLastName(patientDTO.lastName);
        patient.setFirstName(patientDTO.firstName);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, PatientDTO patientDTO){
        Patient patient = getPatientById(id);
        patient.setLastName(patientDTO.lastName == null ? patient.getLastName() : patientDTO.lastName);
        patient.setFirstName(patientDTO.firstName == null ? patient.getFirstName() : patientDTO.firstName);
        return patientRepository.save(patient);
    }

    public Patient deletePatient(Long id){
        Patient patient = getPatientById(id);
        patientRepository.deleteById(id);
        return patient;
    }
}
