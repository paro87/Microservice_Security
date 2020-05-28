package com.paro.clientauthorizationcodegt.model;

import com.paro.clientauthorizationcodegt.protectedResourcesModel.Department;
import com.paro.clientauthorizationcodegt.protectedResourcesModel.Hospital;
import com.paro.clientauthorizationcodegt.protectedResourcesModel.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthAppSearch {
    private String hospitalId;
    private String departmentId;
    private String patientId;

}
