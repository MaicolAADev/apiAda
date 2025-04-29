package com.example.ada.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
    private Long companyId;

    @NotBlank(message = "Company code is mandatory")
    private String codeCompany;

    @NotBlank(message = "Company name is mandatory")
    private String nameCompany;

    private String descriptionCompany;
}