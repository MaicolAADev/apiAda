package com.example.ada.service;

import com.example.ada.dto.CompanyDto;
import com.example.ada.dto.CompanyVersionInfoDto;
import com.example.ada.exception.ResourceNotFoundException;
import com.example.ada.labels.CompanyLabels;
import com.example.ada.model.Company;
import com.example.ada.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
        return convertToDto(company);
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        if (companyRepository.existsByCodeCompany(companyDto.getCodeCompany())) {
            throw new IllegalArgumentException(CompanyLabels.err_company_code_exist);
        }

        Company company = convertToEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return convertToDto(savedCompany);
    }

    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));

        if (!existingCompany.getCodeCompany().equals(companyDto.getCodeCompany()) &&
                companyRepository.existsByCodeCompany(companyDto.getCodeCompany())) {
            throw new IllegalArgumentException("Company code already exists");
        }

        existingCompany.setCodeCompany(companyDto.getCodeCompany());
        existingCompany.setNameCompany(companyDto.getNameCompany());
        existingCompany.setDescriptionCompany(companyDto.getDescriptionCompany());

        Company updatedCompany = companyRepository.save(existingCompany);
        return convertToDto(updatedCompany);
    }

    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }

    public CompanyVersionInfoDto getCompanyVersionInfo(String codeCompany) {
        Company company = companyRepository.findByCodeCompany(codeCompany)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with code: " + codeCompany));

        CompanyVersionInfoDto dto = new CompanyVersionInfoDto();
        dto.setCodeCompany(company.getCodeCompany());
        dto.setNameCompany(company.getNameCompany());
        dto.setAppName("");
        dto.setVersion("");

        return dto;
    }

    private CompanyDto convertToDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }

    private Company convertToEntity(CompanyDto companyDto) {
        return modelMapper.map(companyDto, Company.class);
    }
}