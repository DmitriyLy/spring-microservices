package net.dmly.license.service.client.impl;

import lombok.RequiredArgsConstructor;
import net.dmly.license.model.Organization;
import net.dmly.license.service.client.OrganizationClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationDiscoveryClient implements OrganizationClient {
    final private DiscoveryClient discoveryClient;

    @Override
    public Organization getOrganization(String organizationId) {
        var restTemplate = new RestTemplate();

        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");

        if (instances == null || instances.isEmpty()) {
            return null;
        }

        String serviceUri = String.format(
                "%s/v1/organization/%s",
                instances.getFirst().getUri().toString(),
                organizationId
        );

        ResponseEntity< Organization > restExchange =
                restTemplate.exchange(
                        serviceUri,
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
