package com.paro.zjwthospitalservice.service;

import com.paro.zjwthospitalservice.model.Department;
import com.paro.zjwthospitalservice.model.Hospital;
import com.paro.zjwthospitalservice.model.Patient;
import com.paro.zjwthospitalservice.repository.HospitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class HospitalService {
    private static final Logger LOGGER= LoggerFactory.getLogger(HospitalService.class);

    private final HospitalRepository hospitalRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public HospitalService(HospitalRepository hospitalRepository, RestTemplate restTemplate){
        this.hospitalRepository=hospitalRepository;
        this.restTemplate = restTemplate;
    }

    public List<Hospital> getAll(){

        List<Hospital> hospitalList=hospitalRepository.findAll();
        LOGGER.info("Hospitals found");
        return hospitalList;
    }

    public Hospital getById(Long hospitalId) {
        Optional<Hospital> hospitalFound=hospitalRepository.findById(hospitalId);
        if (hospitalFound.isPresent()) {
            LOGGER.info("Hospital found with id={}", hospitalId);
            return hospitalFound.get();
        }
        return null;

    }

    public Hospital add(Hospital hospital){
        Hospital hospitalSaved=hospitalRepository.save(hospital);
        LOGGER.info("Hospital added with id={}", hospital.getId());
        return hospitalSaved;
    }

    /*public Hospital put(Hospital hospital) {
        Hospital hospitalSaved= hospitalRepository.save(hospital);
        LOGGER.info("Hospital put with id={}", hospital.getId());
        return hospitalSaved;
    }

    public Hospital patch(Long hospitalId, Hospital hospitalToPatch) {
        Hospital hospitalFound=hospitalRepository.findById(hospitalId).get();
        if (hospitalToPatch.getId()!=null) {
            hospitalFound.setId(hospitalToPatch.getId());
        }
        if (hospitalToPatch.getName()!=null) {
            hospitalFound.setName(hospitalToPatch.getName());
        }
        if (hospitalToPatch.getAddress()!=null) {
            hospitalFound.setAddress(hospitalToPatch.getAddress());
        }
        //There’s no way of removing or adding a subset of items from a collection.
        //If the hospital wants to add or remove an entry from a collection, it must send the complete altered collection.
        if (hospitalToPatch.getPatientList()!=null) {
            hospitalFound.setPatientList(hospitalToPatch.getPatientList());
        }
        if (hospitalToPatch.getDepartmentList()!=null) {
            hospitalFound.setDepartmentList(hospitalToPatch.getDepartmentList());
        }
        Hospital hospitalPatched= hospitalRepository.save(hospitalFound);
        LOGGER.info("Hospital patched with id={}", hospitalFound.getId());
        return hospitalPatched;
    }

    public void deleteById(Long hospitalId) {
        try {
            hospitalRepository.deleteById(hospitalId);
            LOGGER.info("Hospital deleted with id={}", hospitalId);
        } catch (EmptyResultDataAccessException e){}
    }
*/
    private String departmentClient="http://localhost:8091";
    private String patientClient="http://localhost:8092";
    private static final String RESOURCE_PATH="/service/hospital/";
    private String REQUEST_URI_Department=departmentClient+RESOURCE_PATH;
    private String REQUEST_URI_Patient=patientClient+RESOURCE_PATH;


    public Hospital getHospitalWithDepartments(Long hospitalId){
        Hospital hospital=hospitalRepository.findById(hospitalId).orElse(null);
        ResponseEntity<List<Department>> responseEntity = restTemplate.exchange(REQUEST_URI_Department+hospital.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Department>>() {});
        hospital.setDepartmentList(responseEntity.getBody());
        LOGGER.info("Departments found with hospital id={}", hospitalId);
        return hospital;
    }

    public Hospital getHospitalWithDepartmentsAndPatients(Long hospitalId){
        Hospital hospital=hospitalRepository.findById(hospitalId).orElse(null);
        ResponseEntity<List<Department>> responseEntity = restTemplate.exchange(REQUEST_URI_Department+hospital.getId()+"/with-patients", HttpMethod.GET, null, new ParameterizedTypeReference<List<Department>>() {});
        hospital.setDepartmentList(responseEntity.getBody());
        LOGGER.info("Departments and patients found with hospital id={}", hospitalId);
        return hospital;
    }

    public Hospital getHospitalWithPatients(Long hospitalId){
        Hospital hospital=hospitalRepository.findById(hospitalId).orElse(null);
        ResponseEntity<List<Patient>> responseEntity = restTemplate.
                exchange(REQUEST_URI_Patient+hospital.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Patient>>() {});
        hospital.setPatientList(responseEntity.getBody());
        LOGGER.info("Patients found with hospital id={}", hospitalId);
        return hospital;
    }

}
