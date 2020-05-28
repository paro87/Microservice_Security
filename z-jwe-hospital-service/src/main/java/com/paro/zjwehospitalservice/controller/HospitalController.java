package com.paro.zjwehospitalservice.controller;

import com.paro.zjwehospitalservice.model.Hospital;
import com.paro.zjwehospitalservice.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/service", produces = "application/json")   //Will only handle requests if the request’s Accept header includes “application/json”, For Swagger UI: http://localhost:8091/swagger-ui.html
@CrossOrigin(origins="*")                                           //Allows clients from any domain to consume the API, especially for frontend
public class HospitalController {

    private final HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService){
        this.hospitalService=hospitalService;
    }

    @GetMapping(value = "/")
    public List<Hospital> getAll(){
        List<Hospital> hospitalsFound=hospitalService.getAll();
        return hospitalsFound;
    }
    @GetMapping(value = "/{id}")
    public Hospital getById(@PathVariable("id") Long hospitalId){
        Hospital hospitalFound=hospitalService.getById(hospitalId);
        return hospitalFound;
    }

    @PostMapping(value = "/", consumes = "application/json")    //Will only handle requests whose Content-type matches application/json
    public Hospital add(@RequestBody Hospital hospital){
        Hospital hospitalAdded=hospitalService.add(hospital);
        return hospitalAdded;
    }

/*
    @PutMapping(value = "/{id}")
    public Hospital putById(@RequestBody Hospital hospital){
        Hospital hospitalPut=hospitalService.put(hospital);
        return hospitalPut;
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public Hospital patchById(@PathVariable("id") Long hospitalId, @RequestBody Hospital hospital){
        Hospital hospitalPatched=hospitalService.patch(hospitalId, hospital);
        return hospitalPatched;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)   // 204
    public void deleteById(@PathVariable("id") Long hospitalId) {
        hospitalService.deleteById(hospitalId);
    }
*/

    @GetMapping(value = "/{id}/with-departments")
    public Hospital getHospitalWithDepartments(@PathVariable("id") Long hospitalId){
        Hospital hospital=hospitalService.getHospitalWithDepartments(hospitalId);
        return hospital;
    }
    @GetMapping(value = "/{id}/with-departments-and-patients")
    public Hospital getHospitalWithDepartmentsAndPatients(@PathVariable("id") Long hospitalId){
        Hospital hospital=hospitalService.getHospitalWithDepartmentsAndPatients(hospitalId);
        return hospital;
    }
    @GetMapping(value = "/{id}/with-patients")
    public Hospital getHospitalWithPatients(@PathVariable("id") Long hospitalId){
        Hospital hospital=hospitalService.getHospitalWithPatients(hospitalId);
        return hospital;
    }

}
