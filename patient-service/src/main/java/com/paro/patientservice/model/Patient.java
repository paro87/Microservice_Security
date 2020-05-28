package com.paro.patientservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class Patient {

    private Long id;

    private String firstname;

    private String surname;

    private Long hospitalId;

    private Long departmentId;
}
