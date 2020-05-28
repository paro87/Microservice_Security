package com.paro.patientservice.repository;

import com.paro.patientservice.model.Patient;

import java.util.List;
import java.util.Optional;


public interface PatientRepository {
    Patient add(Patient patient);

    List<Patient> findByDepartmentId(Long departmentId);

    List<Patient> findByHospitalId(Long hospitalId);

    Optional<Patient> findById(Long patientId);

    List<Patient> findAll();
}
