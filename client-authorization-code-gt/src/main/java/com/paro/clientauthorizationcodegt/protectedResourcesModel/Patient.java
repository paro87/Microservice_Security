package com.paro.clientauthorizationcodegt.protectedResourcesModel;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Patient {
    private Long id;
    private String firstname;
    private String surname;
    private Long hospitalId;
    private Long departmentId;
}
