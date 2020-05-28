package com.paro.departmentservice.repository;

import com.paro.departmentservice.model.Department;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepositoryImplementation implements DepartmentRepository {
    List<Department> departmentList=new ArrayList<>();

    @Override
    public Department add(Department department) {
        departmentList.add(department);
        return department;
    }

    @Override
    public List<Department> findByHospitalId(Long hospitalId) {
        List<Department> departmentsByHospitalId=new ArrayList<>();
        for (Department department:departmentList) {
            if (department.getHospitalId()==hospitalId)
                departmentsByHospitalId.add(department);
        }

        return departmentsByHospitalId;
    }

    @Override
    public List<Department> findAll() {
        return departmentList;
    }

    @Override
    public Optional<Department> findById(Long departmentId) {
        Department departmentById=null;
        for (Department department : departmentList) {
            if (department.getId().equals(departmentId))
                departmentById=department;
        }
        return Optional.ofNullable(departmentById);
    }
}
