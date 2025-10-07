package net.dmly.organization.service;

import lombok.RequiredArgsConstructor;
import net.dmly.organization.model.Organization;
import net.dmly.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        return (opt.isPresent()) ? opt.get() : null;
    }

    public Organization create(Organization organization){
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        return organization;

    }

    public void update(String organizationId, Organization organization){
        //TODO check entity exists
        repository.save(organization);
    }

    public void delete(String organizationId){
        //TODO check entity exists
        repository.deleteById(organizationId);
    }

}
