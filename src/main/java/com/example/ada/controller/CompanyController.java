package com.example.ada.controller;

import com.example.ada.dto.CompanyDto;
import com.example.ada.dto.CompanyVersionInfoDto;
import com.example.ada.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController extends BaseController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCompanies() {
        try {
            List<CompanyDto> companies = companyService.getAllCompanies();
            return successResponse("Companies retrieved successfully", companies);
        }
        catch(Exception e)
        {
            return this.responseInternalServerError(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        try {
            CompanyDto company = companyService.getCompanyById(id);
            return successResponse("Company retrieved successfully", company);
        }
        catch (Exception e)
        {
            return responseInternalServerError(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto createdCompany = companyService.createCompany(companyDto);
        return createdResponse("Company created successfully", createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        CompanyDto updatedCompany = companyService.updateCompany(id, companyDto);
        return successResponse("Company updated successfully", updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
      try
      {
          companyService.deleteCompany(id);
          return noContentResponse("Company deleted successfully");
      }
      catch (Exception e)
      {
          return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    }

    @GetMapping("/version-info/{codeCompany}")
    public ResponseEntity<?> getCompanyVersionInfo(@PathVariable String codeCompany) {
        CompanyVersionInfoDto info = companyService.getCompanyVersionInfo(codeCompany);
        return successResponse("Version info retrieved successfully", info);
    }

    private ResponseEntity<?> responseInternalServerError(Exception e)
    {
        return errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
