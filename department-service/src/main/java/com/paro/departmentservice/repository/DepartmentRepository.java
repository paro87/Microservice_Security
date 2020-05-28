package com.paro.departmentservice.repository;

import com.paro.departmentservice.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository  {
    Department add(Department department);
    List<Department> findByHospitalId(Long hospitalId);

    List<Department> findAll();

    Optional<Department> findById(Long departmentId);
}
