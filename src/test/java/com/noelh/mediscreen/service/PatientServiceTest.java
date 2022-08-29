package com.noelh.mediscreen.service;

import com.noelh.mediscreen.dto.PatientDTO;
import com.noelh.mediscreen.model.Patient;
import com.noelh.mediscreen.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    public void getPatientList_Should_Return_PatientList(){
        //Given


        //When
        patientService.getPatientList();

        //Then
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void getPatientById_Should_Return_Patient(){
        //Given
        when(patientRepository.existsById(1L)).thenReturn(true);

        //When
        patientService.getPatientById(1L);

        //Then
        verify(patientRepository, times(1)).getReferenceById(1L);
    }

    @Test
    public void getPatientById_Should_Return_Exception(){
        //Given
        when(patientRepository.existsById(1L)).thenReturn(false);

        //When
        //Then
        assertThrows(EntityNotFoundException.class, () -> patientService.getPatientById(1L));
    }

    @Test
    public void addPatient_Should_Return_Patient(){
        //Given
        PatientDTO patientDTO = new PatientDTO(
                "TestLastName",
                "TestFirstName");

        Patient patient = new Patient();
        patient.setLastName("TestLastName");
        patient.setFirstName("TestFirstName");

        //When
        patientService.addPatient(patientDTO);

        //Then
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void updatePatient_Should_Return_Exception(){
        //Given
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setLastName("TestLastName");
        patientDTO.setFirstName("TestFirstName");

        when(patientRepository.existsById(1L)).thenReturn(false);

        //When
        //Then
        assertThrows(EntityNotFoundException.class, () -> patientService.updatePatient(1L,patientDTO));
    }

    @Test
    public void updatePatient_Should_Return_Patient(){
        //Given
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setLastName("TestLastName");
        patientDTO.setFirstName("TestFirstName");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setLastName("TestLastName");
        patient.setFirstName("TestFirstName");

        when(patientRepository.existsById(1L)).thenReturn(true);
        when(patientService.getPatientById(1L)).thenReturn(patient);

        //When
        patientService.updatePatient(1L, patientDTO);

        //Then
        verify(patientRepository,times(1)).save(patient);
    }

    @Test
    public void updatePatient_Without_LastName_And_FirstName_Should_Return_Patient(){
        //Given
        PatientDTO patientDTO = new PatientDTO();

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setLastName("TestLastName");
        patient.setFirstName("TestFirstName");

        when(patientRepository.existsById(1L)).thenReturn(true);
        when(patientService.getPatientById(1L)).thenReturn(patient);

        //When
        patientService.updatePatient(1L, patientDTO);

        //Then
        verify(patientRepository,times(1)).save(patient);
    }

    @Test
    public void deletePatient_Should_Return_Patient(){
        //Given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setLastName("TestLastName");
        patient.setFirstName("TestFirstName");

        when(patientRepository.existsById(1L)).thenReturn(true);
        when(patientService.getPatientById(1L)).thenReturn(patient);

        //When
        patientService.deletePatient(1L);

        //Then
        verify(patientRepository,times(1)).deleteById(1L);
    }

    @Test
    public void deletePatient_Should_Return_Exception(){
        //Given
        when(patientRepository.existsById(1L)).thenReturn(false);

        //When
        //Then
        assertThrows(EntityNotFoundException.class, () -> patientService.deletePatient(1L));
    }

}