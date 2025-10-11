package net.dmly.license.service.client.impl;

import lombok.RequiredArgsConstructor;
import net.dmly.license.model.Organization;
import net.dmly.license.service.client.OrganizationClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OrganizationRestTemplateClient implements OrganizationClient {

    private final RestTemplate restTemplate;

    @Override
    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> response = restTemplate.exchange(
                "http://organization-service/v1/organization/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId
        );

        return response.getBody();
    }
}
