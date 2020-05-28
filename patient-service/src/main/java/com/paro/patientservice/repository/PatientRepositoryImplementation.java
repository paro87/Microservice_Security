package com.paro.patientservice.repository;

import com.paro.patientservice.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImplementation implements PatientRepository {
    List<Patient> patientList = new ArrayList<>();


    @Override
    public Patient add(Patient patient) {
        patientList.add(patient);
        return patient;
    }


    @Override
    public List<Patient> findByDepartmentId(Long departmentId) {
        List<Patient> patientsByDepartment = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getDepartmentId() == departmentId)
                patientsByDepartment.add(patient);
        }
        return patientsByDepartment;
    }

    @Override
    public List<Patient> findByHospitalId(Long hospitalId) {
        List<Patient> patientsByHospital = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getHospitalId() == hospitalId)
                patientsByHospital.add(patient);
        }
        return patientsByHospital;
    }

    @Override
    public Optional<Patient> findById(Long patientId) {
        Patient patientById=null;
        for (Patient patient : patientList) {
            if (patient.getId().equals(patientId))
                patientById=patient;
        }
        return Optional.ofNullable(patientById);
    }

    @Override
    public List<Patient> findAll() {
        return patientList;
    }
}
