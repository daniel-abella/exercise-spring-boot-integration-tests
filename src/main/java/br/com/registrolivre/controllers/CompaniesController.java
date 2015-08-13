package br.com.registrolivre.controllers;

import br.com.registrolivre.controllers.representations.CompanyRepresentation;
import br.com.registrolivre.models.Company;
import br.com.registrolivre.repository.CompanyRepository;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.ResponseEntity.ok;

@Log4j
@NoArgsConstructor
@Controller
public class CompaniesController {

    private CompanyRepository companyRepository;

    @Autowired
    public CompaniesController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @RequestMapping(value = "/empresas", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Iterable<CompanyRepresentation>> getCompanies() {
        Iterable<Company> registeredCompanies = companyRepository.findAll();

        List<CompanyRepresentation> companies = StreamSupport.stream(registeredCompanies.spliterator(), false)
                .map(company -> toRepresentation(company))
                .collect(Collectors.toList());
        return ok(companies);
    }

    private CompanyRepresentation toRepresentation(Company company) {
        return CompanyRepresentation.toRepresentation(company);
    }
}