package com.paro.departmentservice.service;

import com.paro.departmentservice.model.Department;
import com.paro.departmentservice.model.Patient;
import com.paro.departmentservice.repository.DepartmentRepository;
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
public class DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
    private final DepartmentRepository departmentRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, RestTemplate restTemplate) {
        this.departmentRepository=departmentRepository;

        this.restTemplate = restTemplate;
    }

    public List<Department> getAll() {
        List<Department> departmentsFound=departmentRepository.findAll();
        LOGGER.info("Departments found");
        return departmentsFound;
    }


    public Department getById(Long departmentId) {
        Optional<Department> departmentFound=departmentRepository.findById(departmentId);
        if (departmentFound.isPresent()) {
            LOGGER.info("Department found with id={}", departmentId);
            return departmentFound.get();
        }
        return null;

    }

    public Department add(Department department){
        Department departmentSaved=departmentRepository.add(department);
        LOGGER.info("Department added with id={}", department.getId());
        return departmentSaved;
    }
/*

    public Department put(Department department) {
        Department departmentSaved= departmentRepository.save(department);
        LOGGER.info("Department put with id={}", department.getId());
        return departmentSaved;
    }

    public Department patch(Long departmentId, Department departmentToPatch) {
        Department departmentFound=departmentRepository.findById(departmentId).get();
        if (departmentToPatch.getId()!=null) {
            departmentFound.setId(departmentToPatch.getId());
        }
        if (departmentToPatch.getName()!=null) {
            departmentFound.setName(departmentToPatch.getName());
        }
        if (departmentToPatch.getHospitalId()!=null) {
            departmentFound.setHospitalId(departmentToPatch.getHospitalId());
        }
        //There’s no way of removing or adding a subset of items from a collection.
        //If the department wants to add or remove an entry from a collection, it must send the complete altered collection.
        if (departmentToPatch.getPatientList()!=null) {
            departmentFound.setPatientList(departmentToPatch.getPatientList());
        }

        Department departmentPatched= departmentRepository.save(departmentFound);
        LOGGER.info("Department patched with id={}", departmentFound.getId());
        return departmentPatched;
    }

    public void deleteById(Long departmentId) {
        try {
            departmentRepository.deleteById(departmentId);
            LOGGER.info("Department deleted with id={}", departmentId);
        } catch (EmptyResultDataAccessException e){}
    }
*/

    public List<Department> getByHospitalId(Long hospitalId){
        List<Department> departmentsFound=departmentRepository.findByHospitalId(hospitalId);
        LOGGER.info("Departments found for the hospital with an id={}", hospitalId);
        return departmentsFound;
    }

    private String patientClient="http://localhost:8092";          //The host address will be fetched from Eureka
    private static final String RESOURCE_PATH="/service/department/";
    private String REQUEST_URI=patientClient+RESOURCE_PATH;



    public List<Department> getByHospitalWithPatients(Long hospitalId){
        List<Department> departmentList=departmentRepository.findByHospitalId(hospitalId);
        List<Patient> patientList;
        for (Department department:departmentList) {
            ResponseEntity<List<Patient>> responseEntity = restTemplate.
                    exchange(REQUEST_URI+department.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Patient>>() {});
            patientList = responseEntity.getBody();
            department.setPatientList(patientList);
        }

        return departmentList;
    }



}
