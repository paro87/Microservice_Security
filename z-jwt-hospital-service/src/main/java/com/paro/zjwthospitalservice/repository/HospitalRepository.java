package com.paro.zjwthospitalservice.repository;


import com.paro.zjwthospitalservice.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository  {
    List<Hospital> findAll();

    Optional<Hospital> findById(Long hospitalId);

    Hospital save(Hospital hospital);
}
