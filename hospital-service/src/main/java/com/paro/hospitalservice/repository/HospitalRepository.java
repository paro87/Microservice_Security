package com.paro.hospitalservice.repository;


import com.paro.hospitalservice.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository  {
    List<Hospital> findAll();

    Optional<Hospital> findById(Long hospitalId);

    Hospital save(Hospital hospital);
}
