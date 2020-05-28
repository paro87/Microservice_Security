package com.paro.zjwehospitalservice.repository;

import com.paro.zjwehospitalservice.model.Hospital;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class HospitalRepositoryImplementation implements HospitalRepository {
    List<Hospital> hospitalList=new ArrayList<>();

    @Override
    public List<Hospital> findAll() {
        return hospitalList;
    }

    @Override
    public Optional<Hospital> findById(Long hospitalId) {
        Hospital hospitalById=null;
        for (Hospital hospital : hospitalList) {
            if (hospital.getId().equals(hospitalId))
                hospitalById=hospital;
        }
        return Optional.ofNullable(hospitalById);
    }

    @Override
    public Hospital save(Hospital hospital) {
        hospitalList.add(hospital);
        return hospital;
    }
}
