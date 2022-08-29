package com.noelh.mediscreen.controller;

import com.noelh.mediscreen.dto.PatientDTO;
import com.noelh.mediscreen.model.Patient;
import com.noelh.mediscreen.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    public void getPatient_Should_Return_Patient_Page(){
        //Given
        Model model = new BindingAwareModelMap();
        when(patientService.getPatientList()).thenReturn(new ArrayList<>());

        //When
        String result = patientController.getPatient(model);

        //Then
        assertThat(result).isEqualTo("patient/Patient");
    }

    @Test
    public void getAddPatient_Should_Return_AddPatient_Page(){
        //Given
        Model model = new BindingAwareModelMap();

        //When
        String result = patientController.getAddPatient(model);

        //Then
        assertThat(result).isEqualTo("patient/AddPatient");
    }

    @Test
    public void postAddPatient_Should_Return_Redirect_Of_Patient_Page(){
        //Given
        PatientDTO patientDTO = new PatientDTO();
        when(patientService.addPatient(patientDTO)).thenReturn(new Patient());

        //When
        String result = patientController.postAddPatient(patientDTO);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void getUpdatePatient_Should_Return_UpdatePatient_Page(){
        //Given
        Model model = new BindingAwareModelMap();
        when(patientService.getPatientById(1L)).thenReturn(new Patient());

        //When
        String result = patientController.getUpdatePatient(1L,model);

        //Then
        assertThat(result).isEqualTo("patient/UpdatePatient");
    }

    @Test
    public void getUpdatePatient_Should_Return_Exception_And_Redirect_To_Patient_Page(){
        //Given
        Model model = new BindingAwareModelMap();
        when(patientService.getPatientById(1L)).thenThrow(new EntityNotFoundException());

        //When
        String result = patientController.getUpdatePatient(1L,model);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void postUpdatePatient_Should_Return_Redirect_To_Patient_Page(){
        //Given
        Patient patient = new Patient();
        PatientDTO patientDTO = new PatientDTO();

        when(patientService.updatePatient(1L, patientDTO)).thenReturn(new Patient());

        //When
        String result = patientController.postUpdatePatient(1L,patient);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void deletePatient_Should_Return_Redirect_Of_Patient_Page(){
        //Given
        when(patientService.deletePatient(1L)).thenReturn(new Patient());

        //When
        String result = patientController.deletePatient(1L);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void deletePatient_Should_Return_Exception_And_Redirect_To_Patient_Page(){
        //Given
        when(patientService.deletePatient(1L)).thenThrow(new EntityNotFoundException());

        //When
        String result = patientController.deletePatient(1L);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

}