package br.com.registrolivre.controllers.representations;

import br.com.registrolivre.models.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Value
@Wither
@FieldDefaults(level = PRIVATE)
public class CompanyRepresentation {

    @JsonFormat
    Long id;
    @JsonFormat
    String cnpj;
    @JsonFormat
    String tradeName;
    @JsonFormat
    Set<DocumentRepresentation> documents;

    public CompanyRepresentation(String cnpj, String tradeName) {
        this.cnpj = cnpj;
        this.tradeName = tradeName;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Value
    @Wither
    @FieldDefaults(level = PRIVATE)
    public static class Builder {

        Long id;
        String cnpj;
        String tradeName;
        Set<DocumentRepresentation> documents;

        public CompanyRepresentation build() {
            return new CompanyRepresentation(null, null, null, null);
        }

        public CompanyRepresentation toRepresentation(Company company) {
            Set<DocumentRepresentation> documents = company.getDocuments().stream()
                    .map(document -> new DocumentRepresentation.Builder().toRepresentantion(document))
                    .collect(Collectors.toSet());

            return new CompanyRepresentation()
                    .withId(company.getId())
                    .withCnpj(company.getCnpj())
                    .withTradeName(company.getTradeName())
                    .withDocuments(documents);
        }
    }
}
