package com.noelh.mediscreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Patient DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    public String lastName;

    public String firstName;
}
