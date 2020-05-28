package com.paro.clientauthorizationcodegt.protectedResourcesModel;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Hospital {
    private Long id;

    private String name;

    private String address;

    private List<Department> departmentList=new ArrayList<>();

    private List<Patient> patientList=new ArrayList<>();

}
