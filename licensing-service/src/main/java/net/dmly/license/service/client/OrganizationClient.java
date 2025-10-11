package net.dmly.license.service.client;

import net.dmly.license.model.Organization;

public interface OrganizationClient {
    Organization getOrganization(String organizationId);
}
